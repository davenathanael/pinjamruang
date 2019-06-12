package app.pinjamruang.room.dto;

public class CreateRoomDto {
    private String name;
    private Integer capacity;
    private String openTime;
    private String closeTime;

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }
}

