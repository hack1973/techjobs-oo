package org.launchcode.controllers;

import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();
    private Job newJob;

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        // Find job by id and pass to view
        Job job = jobData.findById(id);
        model.addAttribute("job", job);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.hasErrors()) {
            System.out.println("Errors = " + errors.getAllErrors().toString());
            return "new-job";
        } else {
            Job newJob = new Job(
                    jobForm.getName(),
                    jobData.getEmployers().findById(jobForm.getEmployerId()),
                    jobData.getLocations().findById(jobForm.getLocationId()),
                    jobData.getPositionTypes().findById(jobForm.getPositionTypeId()),
                    jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId())
            );

            jobData.add(newJob);

            model.addAttribute("name", newJob);

            return "redirect:/job?id=" + newJob.getId();
        }



//        newJob.setName(jobForm.getName());
//        newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
//        newJob.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
//        newJob.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));
//        newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetenciesId()));

//        jobData.add(newJob);
//
//        model.addAttribute("name", newJob);

//        model.addAttribute("name", newJob.getName());
//        model.addAttribute("employer", newJob.getEmployer());
//        model.addAttribute("location", newJob.getLocation());
//        model.addAttribute("position", newJob.getPositionType());
//        model.addAttribute("skill", newJob.getCoreCompetency());
//
//
//        return "job-detail";

//        return "redirect:/job?id=" + newJob.getId();
    }
}
