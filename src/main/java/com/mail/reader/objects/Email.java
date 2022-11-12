package com.mail.reader.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Email implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private Long id;
    public String subject;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(columnDefinition="TEXT")
    public List<String> body;
}
