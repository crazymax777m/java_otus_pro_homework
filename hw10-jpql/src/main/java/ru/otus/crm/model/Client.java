package ru.otus.crm.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.LAZY, mappedBy = "client", orphanRemoval = true)
    private List<Phone> phones = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private Address address;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones.addAll(phones);

        if (address != null) {
            address.setClient(this);
        }

        phones.forEach(p -> p.setClient(this));
    }

    @Override
    public Client clone() {

        Address unProxiedAddress = this.address;
        if(this.address instanceof HibernateProxy) {
            unProxiedAddress = Hibernate.unproxy(this.address, Address.class);
        }

        return new Client(this.id, this.name, unProxiedAddress, this.phones);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}