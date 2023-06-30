package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Employee> list = parseXML("data.xml");
        String answer = listToJson(list);
        writeString(answer,"data.json");
    }

    public static List<Employee> parseXML(String fileName){
        List<String> list = new ArrayList<>();
        List<Employee> answer = new ArrayList<>();

        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(fileName));

            Node root = doc.getDocumentElement();
            NodeList nodeList = root.getChildNodes();


            for (int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                NodeList nodeList2 = node.getChildNodes();
                for (int j = 0; j < nodeList2.getLength(); j++){
                    Node node2 = nodeList2.item(j);
                    if (node2.getNodeType() == 1) {
                        list.add(node2.getTextContent().toString());
                    }
                }
            }
            for (int i = 0; i<list.size(); i=i+5){
                answer.add(new Employee(Long.parseLong(list.get(i)), list.get(i+1), list.get(i+2), list.get(i+3), Integer.parseInt(list.get(i+4))));
            }
        } catch (IOException | ParserConfigurationException | SAXException ex){
            ex.getMessage();
        }

        return answer;
    }

    public static <T> String listToJson(List<T> list){
        Type listType = new TypeToken<List<T>>() {}.getType();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String answer = gson.toJson(list, listType);
        return answer;
    }

    public static void writeString(String answer, String toFileName){
        File newfile = new File(toFileName);
        try (FileWriter writer = new FileWriter(newfile, false)){
            writer.write(answer);
        } catch (IOException ex){
            ex.getMessage();
        }
    }
}