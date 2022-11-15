package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

    //resources 밑의 messages 밑에 message properties 파일을 들고 있다.
    @Autowired
    MessageSource ms;

    //번역되는 부분
    @Test
    void helloMessage() {
        String result = ms.getMessage("hello", null, null);
        assertThat(result).isEqualTo("안녕");
    }
    
    //해당하는 message 있는지 없는지 판단.
    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    //기본 메세지
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메세지", null);
        assertThat(result).isEqualTo("기본 메세지");
    }

    @Test
    void argumentMessage() {
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }

}
