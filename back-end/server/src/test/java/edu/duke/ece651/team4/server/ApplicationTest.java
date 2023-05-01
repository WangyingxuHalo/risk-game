package edu.duke.ece651.team4.server;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class ApplicationTest {

    @Test
    public void main() {
        Application.main(new String[]{});
        assertTrue(true);
    }

}