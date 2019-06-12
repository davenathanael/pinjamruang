package app.pinjamruang.reservation.service;

import app.pinjamruang.reservation.dto.CreateReservationDto;
import app.pinjamruang.reservation.exception.RoomNotAvailableException;
import app.pinjamruang.reservation.model.Reservation;
import app.pinjamruang.reservation.repository.ReservationRepository;
import app.pinjamruang.room.model.Room;
import app.pinjamruang.room.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationServiceTest {

    @InjectMocks
    ReservationService service;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    RoomService roomService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = RoomNotAvailableException.class)
    public void createReservation_notEnoughCapacity_throwsRoomNotAvailableException() {
        Room room = new Room();
        room.setCapacity(9);
        room.setOpenTime(LocalTime.now());
        room.setCloseTime(LocalTime.now());
        Mockito.when(roomService.getRoomById(1L)).thenReturn(room);
        CreateReservationDto dto = new CreateReservationDto("2019-06-12 09:00", "2019-06-12 09:10", 10, "Testing", 1L);
        Reservation result = service.createReservation(dto);

        Mockito.verifyZeroInteractions(reservationRepository);
    }

    @Test(expected = RoomNotAvailableException.class)
    public void createReservation_roomNotOnOperationalTime_throwsRoomNotAvailableException() {
        Room room = new Room();
        room.setCapacity(10);
        room.setOpenTime(LocalTime.now());
        room.setCloseTime(LocalTime.now());
        Mockito.when(roomService.getRoomById(1L)).thenReturn(room);
        CreateReservationDto dto = new CreateReservationDto("2019-06-12 09:00", "2019-06-12 09:10", 10, "Testing", 1L);
        Reservation result = service.createReservation(dto);

        Mockito.verifyZeroInteractions(reservationRepository);
    }

    @Test(expected = RoomNotAvailableException.class)
    public void createReservation_roomIsAlreadyReserved_throwsRoomNotAvailableException() {
        Room room = new Room();
        room.setCapacity(10);
        room.setOpenTime(LocalTime.parse("09:00"));
        room.setCloseTime(LocalTime.parse("18:00"));
        Mockito.when(roomService.getRoomById(1L)).thenReturn(room);
        CreateReservationDto dto = new CreateReservationDto("2019-06-12 09:00", "2019-06-12 09:10", 10, "Testing", 1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDate = LocalDateTime.parse("2019-06-12 09:00", formatter);
        LocalDateTime endDate = LocalDateTime.parse("2019-06-12 09:10", formatter);

        List<Reservation> dummy = new ArrayList<>();
        dummy.add(new Reservation());
        Mockito.when(reservationRepository.findByStartDateBetweenAndEndDateBetween(startDate, endDate, startDate, endDate)).thenReturn(dummy);

        Reservation result = service.createReservation(dto);
        Mockito.verifyZeroInteractions(reservationRepository);
    }

    @Test(expected = RoomNotAvailableException.class)
    public void createReservation_reservationWithinOneDay_throwsRoomNotAvailabeException(){
        Room room = new Room();
        room.setCapacity(10);
        room.setOpenTime(LocalTime.parse("09:00"));
        room.setCloseTime(LocalTime.parse("18:00"));
        Mockito.when(roomService.getRoomById(1L)).thenReturn(room);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        CreateReservationDto dto = new CreateReservationDto("2019-06-12 09:00","2019-06-13 09:00", 10, "Testing", 1L);
        Reservation result = service.createReservation(dto);

        Mockito.verifyZeroInteractions(reservationRepository);
    }

    @Test
    public void createReservation_Success() {
        Room room = new Room();
        room.setCapacity(20);
        room.setOpenTime(LocalTime.parse("09:00"));
        room.setCloseTime(LocalTime.parse("18:00"));
        Mockito.when(roomService.getRoomById(1L)).thenReturn(room);

        String startDate = "2019-06-12 10:00";
        String endDate = "2019-06-12 12:00";
        String agenda = "Testing";
        long roomId = 1L;
        CreateReservationDto dto = new CreateReservationDto(startDate, endDate, 10, agenda, roomId);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startLocalDateTime = LocalDateTime.parse(startDate, formatter);
        LocalDateTime endLocalDateTime = LocalDateTime.parse(endDate, formatter);

        Reservation dummy = new Reservation(startLocalDateTime, endLocalDateTime, 10, agenda, room);
        Mockito.when(reservationRepository.save(dummy)).thenReturn(dummy);

    }

}
