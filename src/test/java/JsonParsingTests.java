import com.fasterxml.jackson.databind.ObjectMapper;
import dataMap.TransactionAtributes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStream;

public class JsonParsingTests {
    final private ClassLoader cl = JsonParsingTests.class.getClassLoader();
    final private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Поиск строки в json файле")
    public void jsonFileParsingTest() throws Exception {
       try (InputStream is = cl.getResourceAsStream("transaction.json")){
           Assertions.assertNotNull(is, "Файл не найден");

           TransactionAtributes transactionAtributes = mapper.readValue(is, TransactionAtributes.class );
           Assertions.assertEquals("Оплата за услуги", transactionAtributes.getDescription());
           Assertions.assertEquals("COMPLETED", transactionAtributes.getStatus());

           //Проверка суммы коммисии для отправителя "Иван Иванов"
           Assertions.assertEquals("Иван Иванов", transactionAtributes.getSender().getName());
           Assertions.assertEquals("commission", transactionAtributes.getOperations().get(0).getType());
           Assertions.assertEquals(Double.parseDouble("50.00"), transactionAtributes.getOperations().get(0).getAmount(), 0.001);
           Assertions.assertEquals("RUB", transactionAtributes.getOperations().get(0).getCurrency());
       }
    }
}