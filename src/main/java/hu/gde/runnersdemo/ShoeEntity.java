package hu.gde.runnersdemo;

import jakarta.persistence.*;

@Entity
public class ShoeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "runner_id")
    private RunnerEntity runner;

    
    public ShoeEntity() {}

    public ShoeEntity(String name, RunnerEntity runner) {
        this.name = name;
        this.runner = runner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RunnerEntity getRunner() {
        return runner;
    }

    public void setRunner(RunnerEntity runner) {
        this.runner = runner;
    }
}
