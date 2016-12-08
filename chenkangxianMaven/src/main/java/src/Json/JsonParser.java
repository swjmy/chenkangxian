package src.Json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Administrator on 2016/11/30.
 */
public class JsonParser {
    public static void main(String[] args){
        Person person = new Person();
        person.setName("zhang");
        person.setAge(16);
        try {
            String json = parser(person);
            System.out.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static String parser(Person person) throws IOException {
        String personJson = null;

        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(writer);
        objectMapper.writeValue(writer,person);
        generator.close();
        personJson = writer.toString();
        return personJson;
    }


}
