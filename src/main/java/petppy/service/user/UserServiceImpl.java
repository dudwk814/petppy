package petppy.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petppy.domain.*;
import petppy.domain.notificaton.Notification;
import petppy.domain.user.Membership;
import petppy.domain.user.Rating;
import petppy.domain.user.Type;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.user.MembershipDTO;
import petppy.dto.user.UserDTO;
import petppy.exception.UserNotFoundException;
import petppy.repository.board.BoardRepository;
import petppy.repository.comment.CommentRepository;
import petppy.repository.enquiry.EnquiryRepository;
import petppy.repository.notification.NotificationRepository;
import petppy.repository.payment.PaymentRepository;
import petppy.repository.reserve.ReserveRepository;
import petppy.repository.user.UserRepository;
import petppy.repository.user.MembershipRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
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
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final PaymentRepository paymentRepository;
    private final EnquiryRepository enquiryRepository;
    private final ReserveRepository reserveRepository;
    private final NotificationRepository notificationRepository;

    /**
     * 멤버십 등급 변경
     * @param userId
     * @param rating
     */
    @Override
    @Transactional
    public void changeMembership(Long userId, String rating) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Membership findMembership = membershipRepository.findByUser(user);

        Rating changeRating = Rating.NONE;

        // 넘어온 멤버십 값에 따라 changeRating 세팅
        if (rating.equals("Personal")) {
            changeRating = Rating.PERSONAL;
        } else if (rating.equals("Business")) {
            changeRating = Rating.BUSINESS;
        } else if (rating.equals("Ultimate")) {
            changeRating = Rating.ULTIMATE;
        }
        findMembership.changeRating(changeRating);

    }

    /**
     * 유저 검색 어드민용
     * @return
     */
    @Override
    public PageResultDTO<UserDTO, User> searchUser(PageRequestDTO pageRequestDTO) {

        Page<User> result = userRepository.searchUser(pageRequestDTO);

        // 페이징 변수들
        int page = result.getNumber() + 1;
        int size = result.getSize();
        int totalPages = result.getTotalPages();
        long totalElements = result.getTotalElements();



        Function<User, UserDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn, totalPages, page, size, totalElements);

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

    /**
     * User Address 변경
     * @param dto
     * @return
     */
    @Override
    @Transactional
    public boolean ModifyUserAddress(UserDTO dto) {
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

            return true;
        } else {
            return false;
        }

    }

    @Override
    @Transactional
    public void disabled(UserDTO dto) {
        commentRepository.deleteByUserId(dto.getId());
        boardRepository.deleteByUserId(dto.getId());
        paymentRepository.deleteByUserId(dto.getId());
        enquiryRepository.deleteByUserId(dto.getId());
        reserveRepository.deleteByUserId(dto.getId());
        notificationRepository.deleteByTargetEmail(dto.getEmail());
        userRepository.deleteByEmail(dto.getEmail());

    }

    @Override
    public UserDTO findByEmail(String email) {

        User findUser = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        return entityToDto(findUser);

    }

    @Override
    public UserDTO findById(Long id) {
        return entityToDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
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

    /**
     * 이메일 중복 체크
     * @param email
     * @return
     */
    public boolean checkEmailExist(String email) {
        Optional<User> findMember = userRepository.findByEmail(email);

        return findMember.isPresent();
    }

    /**
     * @param userDTO
     */
    @Override
    @Transactional
    public boolean changePassword(UserDTO userDTO) {

        if (userDTO.getPassword().length() < 4 || userDTO.getPassword().length() > 16) {
            return false;
        }

        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(UserNotFoundException::new);
        user.changePassword(passwordEncoder.encode(userDTO.getPassword()));

        return true;
    }



}
