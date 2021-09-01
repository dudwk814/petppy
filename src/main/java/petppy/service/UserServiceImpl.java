package petppy.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.*;
import petppy.domain.user.Type;
import petppy.domain.user.User;
import petppy.dto.MembershipDTO;
import petppy.dto.UserDTO;
import petppy.repository.UserRepository;
import petppy.repository.MembershipRepository;

import java.util.List;
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
    public void changeMembership(UserDTO dto, Rating rating) {
        Membership findMembership = membershipRepository.findByUser(dtoToEntity(dto));
        findMembership.changeRating(rating);

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
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO findByEmail(String email) {

        User findUser = userRepository.findByEmail(email).get();

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

    public boolean checkMemberIdExist(UserDTO userDTO) {
        Optional<User> findMember = userRepository.findByEmail(userDTO.getEmail());

        if (findMember.isPresent()) {
            System.out.println("findMember.get() = " + findMember.get());
        }

        return findMember.isPresent();
    }
}
