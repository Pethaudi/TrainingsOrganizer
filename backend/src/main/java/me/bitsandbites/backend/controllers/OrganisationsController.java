package me.bitsandbites.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.bitsandbites.backend.annotations.RequiredAuthOrganisationId;
import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.dtos.Role;
import me.bitsandbites.backend.entities.*;
import me.bitsandbites.backend.helpers.TokenParser;
import me.bitsandbites.backend.repositories.CourseRegisterRepository;
import me.bitsandbites.backend.repositories.CourseTrainerRepository;
import me.bitsandbites.backend.repositories.MembersOfOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("organisations")
public class OrganisationsController {
    private final MembersOfOrganisationRepository membersOfOrganisationRepository;
    private final CourseTrainerRepository courseTrainerRepository;
    private final CourseRegisterRepository courseRegisterRepository;

    @Autowired
    public OrganisationsController(
            MembersOfOrganisationRepository membersOfOrganisationRepository,
            CourseTrainerRepository courseTrainerRepository,
            CourseRegisterRepository courseRegisterRepository
    ) {
        this.membersOfOrganisationRepository = membersOfOrganisationRepository;
        this.courseTrainerRepository = courseTrainerRepository;
        this.courseRegisterRepository = courseRegisterRepository;
    }

    @GetMapping()
    @RequiresAuth()
    public List<Organisation> getOrganisationsOfUser(HttpServletRequest httpServletRequest) {
        var user = TokenParser.parseFromRequest(httpServletRequest);
        return StreamSupport.stream(
                this.membersOfOrganisationRepository.findByRegisteredId(user.getId(), MemberOfOrganisationView.class).spliterator(),
                false
        ).map(MemberOfOrganisationView::getOrganisation).distinct().toList();
    }

    @GetMapping("/{organisationId}/as-trainer")
    @RequiresAuth(role = Role.trainer)
    public List<Course> getTrainerCoursesOfUserPerOrganisation(HttpServletRequest httpServletRequest, @RequiredAuthOrganisationId @PathVariable Integer organisationId) {
        return this.courseTrainerRepository.findAllByTrainerIdAndOrganisationId(
                    TokenParser.parseFromRequest(httpServletRequest).getId(),
                    organisationId
                )
                .stream()
                .map(CourseTrainer::getCourse)
                .toList();
    }

    @GetMapping("/{organisationId}/as-member")
    @RequiresAuth(role = Role.member)
    public List<Course> getMemberCoursesOfUserPerOrganisation(HttpServletRequest httpServletRequest, @RequiredAuthOrganisationId @PathVariable Integer organisationId) {
        return this.courseRegisterRepository.findAllByHandlerIdAndOrganisationId(
                        TokenParser.parseFromRequest(httpServletRequest).getId(),
                        organisationId
                )
                .stream()
                .map(CourseRegister::getCourse)
                .toList();
    }
}
