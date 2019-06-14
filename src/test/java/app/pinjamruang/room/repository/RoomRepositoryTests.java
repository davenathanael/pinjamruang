package app.pinjamruang.room.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static app.pinjamruang.TestUtils.createDummyRoom;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RoomRepositoryTests {
    @Autowired
    RoomRepository repository;

    @Test
    public void jpaRepositoryBasicTest() {
        repository.save(createDummyRoom());
    }
}
