package company.review.demo.experience.users.database;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "MW_TABLE")
public class PostEntity {
    @Id
    @Column(name = "id")
    public Long id;
    @Column(name = "userId")
    public Long userId;
    @Column(name = "title")
    public String title;
    @Column(name = "body", length = 2047)
    public String body;
    @Column(name = "deleted", nullable = false)
    public Boolean deleted = false;
    @Column(name = "edited", nullable = false)
    public Boolean edited = false;

    @Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof PostEntity)) {
            return false;
        }
        PostEntity otherMember = (PostEntity)anObject;
        return otherMember.id.equals(id);
    }

}
