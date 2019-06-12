package app.pinjamruang.room.service;

import app.pinjamruang.room.dto.CreateRoomDto;
import app.pinjamruang.room.model.Room;
import app.pinjamruang.room.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RoomService {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    @Autowired
    private RoomRepository repository;

    public List<Room> getAllRooms() {
        return this.repository.findAll();
    }

    public Room getRoomById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can\'t found room with id %d", id)));
    }

    public Room createRoom(CreateRoomDto roomDto) {
        Room newRoom = new Room();

        newRoom.setName(roomDto.getName());
        newRoom.setCapacity(roomDto.getCapacity());
        newRoom.setOpenTime(LocalTime.parse(roomDto.getOpenTime(), formatter));
        newRoom.setCloseTime(LocalTime.parse(roomDto.getCloseTime(), formatter));

        return this.repository.save(newRoom);
    }

    public Room updateRoom(Long roomId, CreateRoomDto roomDto) {
        return this.repository.findById(roomId)
                .map(oldRoom -> {
                    oldRoom.setName(roomDto.getName());
                    oldRoom.setCapacity(roomDto.getCapacity());
                    oldRoom.setOpenTime(LocalTime.parse(roomDto.getOpenTime(), formatter));
                    oldRoom.setCloseTime(LocalTime.parse(roomDto.getCloseTime(), formatter));
                    return this.repository.save(oldRoom);
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Can\'t found room with id %d", roomId)));
    }

    public void deleteRoom(Long roomId) {
        this.repository.deleteById(roomId);
    }
}
