package com.seidor.exercise2.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name ="builds")
public class Build {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buildId;

    @NotNull
    @Size(min = 2, max = 15)
    @Pattern(regexp = "^[a-zA-Z]$", message = "Name format not valid")
    private String name;

    @NotNull
    @Pattern(regexp = "^\\/[/.a-zA-Z0-9-]+$", message = "Path repository not valid")
    private String pathRepo;

    @NotNull
    @Pattern(regexp = "^(\\d+\\.)?(\\d+\\.)?(\\*|\\d)$", message = "Version number not valid")
    private String version;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    public Long getBuildId() {
        return buildId;
    }

    public String getName() {
        return name;
    }

    public String getPathRepo() {
        return pathRepo;
    }

    public String getVersion() {
        return version;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setBuildId(Long buildId) {
        this.buildId = buildId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPathRepo(String pathRepo) {
        this.pathRepo = pathRepo;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
