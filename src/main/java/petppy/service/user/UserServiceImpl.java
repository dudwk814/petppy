package petppy.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import petppy.domain.*;
import petppy.domain.user.Membership;
import petppy.domain.user.Rating;
import petppy.domain.user.Type;
import petppy.domain.user.User;
import petppy.dto.user.MembershipDTO;
import petppy.dto.user.UserDTO;
import petppy.exception.UserNotFoundException;
import petppy.repository.user.UserRepository;
import petppy.repository.user.MembershipRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static petppy.domain.user.Role.MEMBER;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void changeMembership(Long userId, String rating) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Membership findMembership = membershipRepository.findByUser(user);

        Rating changeRating = Rating.NONE;

        if (rating.equals("Personal")) {
            changeRating = Rating.PERSONAL;
        } else if (rating.equals("Business")) {
            changeRating = Rating.BUSINESS;
        } else if (rating.equals("Ultimate")) {
            changeRating = Rating.ULTIMATE;
        }
        findMembership.changeRating(changeRating);

    }

    @Override
    public MembershipDTO findMembership(Long userId) {

        User user = User.builder()
                .id(userId).build();

        Membership findMembership = membershipRepository.findByUser(user);

        return MembershipDTO
                .builder()
                .id(findMembership.getId())
                .createdDate(findMembership.getCreatedDate())
                .modifiedDate(findMembership.getModifiedDate())
                .rating(findMembership.getRating()).build();
    }

    @Override
    public UserDTO findByEmailAndType(String email, Type type) {

        Optional<User> result = userRepository.findByEmailAndType(email, type);

        User user = result.orElse(null);

        return UserDTO.builder()
                .user(user)
                .build();
    }

    @Override
    @Transactional
    public String joinedMember(UserDTO dto) {

        dto.setRole(MEMBER);

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        User user = dtoToEntity(dto);
        user.addMembership(Membership
                .builder()
                .user(user)
                .build());
        userRepository.save(user);

        return dto.getName();
    }

    @Override
    @Transactional
    public void ModifyUserAddress(UserDTO dto) {
        Optional<User> result = userRepository.findByEmail(dto.getEmail());

        if (result.isPresent()) {
            User findUser = result.get();

            findUser.changeAddress(
                    Address.builder()
                            .postcode(dto.getPostcode())
                            .roadAddress(dto.getRoadAddress())
                            .jibunAddress(dto.getJibunAddress())
                            .detailAddress(dto.getDetailAddress())
                            .extraAddress(dto.getExtraAddress())
                            .build());
        }


    }

    @Override
    @Transactional
    public void deleteMember(String id) {
        userRepository.deleteByEmail(id);
    }

    @Override
    public UserDTO findByEmail(String email) {

        User findUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return entityToDto(findUser);

    }

    @Override
    public List<UserDTO> findMemberListByName(String name, Pageable pageable) {

        List<User> findUsers = userRepository.findByNameContaining(name, pageable);

        List<UserDTO> result = findUsers.stream()
                .map(member -> entityToDto(member))
                .collect(Collectors.toList());

        return result;
    }

    @Override
    public List<UserDTO> findAll(Pageable pageable) {

        Page<User> findAll = userRepository.findAll(pageable);

        List<UserDTO> result = findAll.stream()
                .map(member -> entityToDto(member))
                .collect(Collectors.toList());

        return result;
    }

    // 회원가입 이메일 항목 중복 체크
    public boolean checkEmailExist(String email) {
        Optional<User> findMember = userRepository.findByEmail(email);

        if (findMember.isPresent()) {
            System.out.println("findMember.get() = " + findMember.get());
        }

        return findMember.isPresent();
    }


    
}
