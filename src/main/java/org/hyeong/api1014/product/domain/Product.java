package org.hyeong.api1014.product.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"attachFiles", "tags"})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String name;

    private int price;

    @ElementCollection
    @Builder.Default
    private Set<AttachFile> attachFiles = new HashSet<>();

    @ElementCollection
    @BatchSize(size = 100)
    @Builder.Default
    private Set<String> tags = new HashSet<>();

    public void addFile(String fileName) {
        attachFiles.add(new AttachFile(attachFiles.size(), fileName));
    }

    public void clearFile() {
        attachFiles.clear();
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void clearTags() {
        tags.clear();
    }
}
