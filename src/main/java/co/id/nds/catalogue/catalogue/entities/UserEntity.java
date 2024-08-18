package co.id.nds.catalogue.catalogue.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "ms_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Column(name = "role_id", nullable = false, length = 5)
    private String roleId;

    @Column(name = "call_number", nullable = false, length = 15)
    private String callNumber;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "deleted_date")
    private Timestamp deletedDate;

    @Column(name = "creator_id")
    private Integer creatorId;

    @Column(name = "updater_id")
    private Integer updaterId;

    @Column(name = "deleter_id")
    private Integer deleterId;

    @Column(name = "rec_status", length = 1)
    private String recStatus;

}
