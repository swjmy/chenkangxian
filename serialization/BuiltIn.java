package chenkangxian.serialization;

import com.alibaba.com.caucho.hessian.io.HessianInput;
import com.alibaba.com.caucho.hessian.io.HessianOutput;

import java.io.*;

/**
 * Created by Administrator on 2016/11/17.
 */
public class BuiltIn {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person person = new Person();
        byte[] personByte = hessianOut(person);
        Person person1 = hessianIn(personByte);
        System.out.print(person1);
    }

    static byte[] out(Person person) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(person);
        byte[] personByte = byteOut.toByteArray();
        return personByte;
    }

    static Person in(byte[] personByte) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(personByte);
        ObjectInputStream objectIn = new ObjectInputStream(byteIn);
        Person person = (Person) objectIn.readObject();

        return person;
    }

    static byte[] hessianOut(Person person) throws IOException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput();
        ho.init(byteOut);
        ho.writeObject(person);
        byte[] bytePerson = byteOut.toByteArray();
        return bytePerson;
    }

    static Person hessianIn(byte[] personByte) throws IOException {
        ByteArrayInputStream byteIn = new ByteArrayInputStream(personByte);
        HessianInput objectIn = new HessianInput(byteIn);
        Person person = (Person) objectIn.readObject();
        return person;
    }

    public static class Person implements Serializable{
        private String name;
        private Integer age;

        public Person(){
            this.name = "zhangsan";
            this.age = 18;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("name:")
                    .append(name)
                    .append("\n")
                    .append("age:")
                    .append(age);

            return sb.toString();
        }
    }
}
