package com.internal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="campsite")
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
@JsonPropertyOrder({
    "id",
    "campSiteName",
    "description"})
public class Campsite implements Serializable {

  @Id
  @Column(name = "id", unique = true)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty(value = "ID of the campsite", dataType = "Long", required = false)
  @JsonProperty("id")
  private Long id;

  @ApiModelProperty(value = "The campsite name with which you want to create the campsite",
      example = "Island",dataType = "String")
  @Column(name = "camp_site_name", nullable = false)
  @JsonProperty("campSiteName")
  private String campSiteName;

  @ApiModelProperty(value = "Short description of campsite.", required = false,
      example = "This is the island site", dataType = "String")
  @Column(name = "description", nullable = true)
  @JsonProperty("description")
  private String description;
}
