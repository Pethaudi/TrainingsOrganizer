package me.bitsandbites.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import me.bitsandbites.backend.annotations.RequiresAuth;
import me.bitsandbites.backend.entities.MemberOfOrganisation;
import me.bitsandbites.backend.entities.Organisation;
import me.bitsandbites.backend.helpers.TokenParser;
import me.bitsandbites.backend.repositories.MembersOfOrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController()
@RequestMapping("organisations")
public class OrganisationsController {
    private final MembersOfOrganisationRepository membersOfOrganisationRepository;

    @Autowired
    public OrganisationsController(MembersOfOrganisationRepository membersOfOrganisationRepository) {
        this.membersOfOrganisationRepository = membersOfOrganisationRepository;
    }

    @GetMapping()
    @RequiresAuth()
    public List<Organisation> getOrganisationsOfUser(HttpServletRequest httpServletRequest) {
        var user = TokenParser.parseFromRequest(httpServletRequest);
        return StreamSupport.stream(
                this.membersOfOrganisationRepository.findByRegisteredId(user.getId()).spliterator(),
                false
        ).map(MemberOfOrganisation::getOrganisation).distinct().toList();
    }
}
