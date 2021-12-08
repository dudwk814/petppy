package petppy.service.reserve;

import petppy.domain.reserve.Reserve;
import petppy.domain.reserve.ReserveType;
import petppy.domain.services.Services;
import petppy.domain.services.ServicesType;
import petppy.domain.user.User;
import petppy.dto.PageRequestDTO;
import petppy.dto.PageResultDTO;
import petppy.dto.reserve.ReserveDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface ReserveService {

    public ReserveDTO createReserve(ReserveDTO dto);

    public ReserveDTO findReserve(Long id);

    public PageResultDTO<ReserveDTO, Reserve> findReserveList(ReserveDTO reserveDTO, PageRequestDTO requestDTO);

    public PageResultDTO<ReserveDTO, Reserve> searchReserve(ReserveDTO reserveDTO, PageRequestDTO requestDTO);

    public List<Integer> findTimeNumberByDate(LocalDateTime start, ServicesType servicesType);

    public void cancelReserve(ReserveDTO reserveDTO);

    public void modifyReserveTime(Long id, LocalDateTime reserveStartDate);

    public Long countReserveToReserveTypeEqualReserve(ReserveType reserveType);

    default ReserveDTO entityToDTO(Reserve reserve) {
        return ReserveDTO
                .builder()
                .id(reserve.getId())
                .reserveType(reserve.getReserveType())
                .servicesId(reserve.getServices().getId())
                .servicesType(reserve.getServices().getServicesType())
                .userId(reserve.getUser().getId())
                .name(reserve.getUser().getName())
                .email(reserve.getUser().getEmail())
                .reserveEndDate(reserve.getReserveEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .reserveStartDate(reserve.getReserveStartDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")))
                .request(reserve.getRequest())
                .address(reserve.getAddress())
                .timeNumber(reserve.getTimeNumber())
                .build();
    }

    default Reserve dtoToEntity(ReserveDTO dto) {

        User user = User.builder().id(dto.getUserId()).build();
        Services services = Services.builder().id(dto.getServicesId()).build();

        String[] reserveStartDateToString = dto.getReserveStartDate().split("-");

        int[] reserveStartDateToInt = new int[5];

        for (int i = 0; i < reserveStartDateToString.length; i++) {

            reserveStartDateToInt[i] = Integer.parseInt(reserveStartDateToString[i]);
        }

        LocalDateTime reserveStartDate = LocalDateTime.of(
                reserveStartDateToInt[0], reserveStartDateToInt[1], reserveStartDateToInt[2], reserveStartDateToInt[3], reserveStartDateToInt[4]
        );

        return Reserve
                .builder()
                .user(user)
                .services(services)
                .reserveStartDate(reserveStartDate)
                .reserveEndDate(reserveStartDate.plusHours(2))
                .reserveType(dto.getReserveType())
                .request(dto.getRequest())
                .timeNumber(dto.getTimeNumber())
                .address(dto.getAddress())
                .build();
    }
}
