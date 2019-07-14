package com.controller;

import com.internal.model.Campsite;
import com.respository.CampsiteRepository;
import com.utils.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(value="Campsite controller", description="Managing campsite")
@RestController
@RequestMapping(Constants.API_ENDPOINT)
public class CampsiteController {

  @Autowired
  private CampsiteRepository campsiteRespository;

  @PostMapping(value = Constants.CREATE_CAMPSITE_ENDPOINT,
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "Creates the campsite")
  public Campsite createCampsite(@NotNull @Valid @RequestBody final Campsite campsite) {

    return campsiteRespository.save(campsite);
  }

  @GetMapping(value = Constants.GET_ALL_CAMPSITES,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Campsite> getCampsite() {

    return campsiteRespository.findAll();
  }
}
