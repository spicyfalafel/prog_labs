package Json;

import Collection.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.charset.Charset;

import java.util.LinkedList;
import java.util.List;

public class JsonStaff {

    public static LinkedList<Product> fromJsonToProductList(File jsonFile)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        InputStreamReader reader = new InputStreamReader(new FileInputStream(jsonFile),
                Charset.forName("UTF-8"));
        StringBuilder builder = new StringBuilder();
        int data = 0;
        String jsonArray = "";
        data = reader.read();
        while(data != -1){
            char theChar = (char) data;
            builder.append(theChar);
            data = reader.read();
        }
        reader.close();
        jsonArray = builder.toString();
        CollectionType javaType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, Product.class);
        return new LinkedList<>(objectMapper.readValue(jsonArray, javaType));
    }

    public static void writeCollectionToFile(List<Product> collection, String resFileName)
            throws FileNotFoundException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String jsonArray = objectMapper.writeValueAsString(collection);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(resFileName)));
            writer.write(jsonArray);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
