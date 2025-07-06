import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("Проверка содержимого zip архива")
public class ParsingZipTests {

    final private ClassLoader cl = ParsingZipTests.class.getClassLoader();
    final private ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("Поиск строки в pdf файле")
    public void pdfFileParsingTest() throws Exception{
        try (
            ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("Archive.zip"));
        ) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) !=null){
                if(entry.getName().endsWith(".pdf")){
                    PDF pdf = new PDF(zis);
                    System.out.println();
                    Assertions.assertEquals("Проверочный текст\n", pdf.text);
                }
            }
        }
    }

    @Test
    @DisplayName("Поиск строки в xlsx файле")
    public void xlsxFileParsingTest() throws Exception {
        try (
                ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("Archive.zip"));
        ) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".xlsx")) {
                    XLS xls = new XLS(zis);
                    String actualResult = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                    Assertions.assertTrue(actualResult.contains("Продемонстрировать навык отладки автотестов в целом, базовое владение основными инструментами отладки в выбранной среде разработки."));
                }
            }
        }
    }

    @Test
    @DisplayName("Поиск строки в csv файле")
    public void csvFileParsingTest() throws Exception {
        try (
                ZipInputStream zis = new ZipInputStream(cl.getResourceAsStream("Archive.zip"));
        ) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                if (entry.getName().endsWith(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> data = csvReader.readAll();
                    Assertions.assertEquals(6, data.size());
                    Assertions.assertArrayEquals(new String[] {"Павел Соловьев","Экономический","2","2","2","2","2"}, data.get(1));
                }
            }
        }
    }
}
