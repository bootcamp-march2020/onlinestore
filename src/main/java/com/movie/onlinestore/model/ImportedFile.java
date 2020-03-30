package com.movie.onlinestore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "imported_file")
public class ImportedFile {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "imported_time")
    private Date importedTime;

    @Column(name = "status_message")
    private String statusMessage;


    public ImportedFile() {
    }

    public ImportedFile(String fileName, String statusMessage) {
        this.fileName = fileName;
        this.importedTime = new Date();
        this.statusMessage = statusMessage;
    }
}
