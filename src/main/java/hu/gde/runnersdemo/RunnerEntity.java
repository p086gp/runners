package hu.gde.runnersdemo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RunnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long runnerId;
    private String runnerName;
    private long averagePace;
    private int age; // életkor

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LapTimeEntity> laptimes = new ArrayList<>();

    @OneToMany(mappedBy = "runner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShoeEntity> shoes = new ArrayList<>();

    public RunnerEntity() {
    }

    public long getRunnerId() {
        return runnerId;
    }

    public String getRunnerName() {
        return runnerName;
    }

    public long getAveragePace() {
        return averagePace;
    }

    public int getAge() { //  életkor lekérdezése
        return age;
    }

    public void setRunnerId(long runnerId) {
        this.runnerId = runnerId;
    }

    public void setRunnerName(String runnerName) {
        this.runnerName = runnerName;
    }

    public void setAveragePace(long averagePace) {
        this.averagePace = averagePace;
    }
    
    public void setAge(int age) { // életkor beállítása
        this.age = age;
    }

    public List<LapTimeEntity> getLaptimes() {
        return laptimes;
    }
    public List<ShoeEntity> getShoes() {
        return shoes;
    }

    public void setShoes(List<ShoeEntity> shoes) {
        this.shoes = shoes;
    }
}
