package hu.gde.runnersdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Controller
public class RunnerController {

    @Autowired
    private RunnerRepository runnerRepository;
    @Autowired
    private LapTimeRepository lapTimeRepository;
    @Autowired
    private ShoeRepository shoeRepository;

    @GetMapping("/runners")
    public String getAllRunners(Model model) {
        List<RunnerEntity> runners = runnerRepository.findAll();
        model.addAttribute("runners", runners);
         // átlagéletkor
         double averageAge = runners.stream()
            .mapToDouble(RunnerEntity::getAge)
            .average()
            .orElse(0.0);
          // Kerekítjük egy tizedesre
         BigDecimal averageAgeRounded = BigDecimal.valueOf(averageAge)
                                                   .setScale(2, RoundingMode.HALF_UP);

        model.addAttribute("averageAge", averageAgeRounded);   
        return "runners";
    }

    @GetMapping("/runner/{id}")
    public String getRunnerById(@PathVariable Long id, Model model) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        RunnerService runnerService = new RunnerService(runnerRepository);
        if (runner != null) {
            model.addAttribute("runner", runner);
            double averageLaptime = runnerService.getAverageLaptime(runner.getRunnerId());
            model.addAttribute("averageLaptime", averageLaptime);
            
            // Cipők hozzáadása
            List<ShoeEntity> shoes = shoeRepository.findByRunner(runner);
            model.addAttribute("shoes", shoes);

            return "runner";
        } else {
            // handle error when runner is not found
            return "error";
        }
    }

    @GetMapping("/runner/{id}/addlaptime")
    public String showAddLaptimeForm(@PathVariable Long id, Model model) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            model.addAttribute("runner", runner);
            LapTimeEntity laptime = new LapTimeEntity();
            laptime.setLapNumber(runner.getLaptimes().size() + 1);
            model.addAttribute("laptime", laptime);
            return "addlaptime";
        } else {
            // handle error when runner is not found
            return "error";
        }
    }
    @PostMapping("/runner/{id}/addlaptime")
    public String addLaptime(@PathVariable Long id, @ModelAttribute LapTimeEntity laptime) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            laptime.setRunner(runner);
            laptime.setId(null);
            runner.getLaptimes().add(laptime);
            runnerRepository.save(runner);
        } else {
            // handle error when runner is not found
        }
        return "redirect:/runner/" + id;
    }

    @GetMapping("/runner/{id}/addshoe")
    public String showAddShoeForm(@PathVariable Long id, Model model) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            model.addAttribute("runner", runner);
            ShoeEntity shoe = new ShoeEntity();
            model.addAttribute("shoe", shoe);
            return "addshoe";
        } else {
            // handle error when runner is not found
            return "error";
        }
    }

    @PostMapping("/runner/{id}/addshoe")
    public String addShoe(@PathVariable Long id, @ModelAttribute ShoeEntity shoe) {
        RunnerEntity runner = runnerRepository.findById(id).orElse(null);
        if (runner != null) {
            shoe.setRunner(runner);
            shoe.setId(null);
            shoeRepository.save(shoe);
        } else {
            // handle error when runner is not found
        }
        return "redirect:/runner/" + id;
    }


    @GetMapping("/runners/average-age")
    public double getAverageAgeOfRunners() {
        List<RunnerEntity> runners = runnerRepository.findAll();
        if (runners.isEmpty()) {
            return 0; // Avoid division by zero
        }

        double sumOfAges = runners.stream()
                                .mapToDouble(RunnerEntity::getAge)
                                .sum();
        return sumOfAges / runners.size();
    }

}

