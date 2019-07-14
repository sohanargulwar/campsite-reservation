package com.internal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservation")
@Entity
@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel
@JsonPropertyOrder({
    "campsite",
    "firstName",
    "lastName",
    "emailID",
    "reservationUUID",
    "arrivalDate",
    "departureDate"
})
public class Reservation implements Serializable {

  @Id
  @Column(name = "uuid", unique = true, nullable = false)
  @ApiModelProperty(value = " the unique reservationUUID for the reservation",
      example = "c4284e15-b22a-4cfb-8493-2ed266464b53", dataType = "String")
  @JsonProperty("reservationUUID")
  private String reservationUUID;

  @Column(name = "first_name", nullable = false)
  //@Pattern(regexp = Constants.REGEX_FOR_STRING_VALIDATION)
  @ApiModelProperty(value = "First name", example = "First Name", dataType = "String")
  @JsonProperty("firstName")
  private String firstName;

  @Column(name = "last_name", nullable = false)
  //@Pattern(regexp = Constants.REGEX_FOR_STRING_VALIDATION)
  @ApiModelProperty(value = "Last name", example = "Last Name", dataType = "String")
  @JsonProperty("lastName")
  private String lastName;

  @Column(name = "email_id", nullable = false)
  //@Pattern(regexp = "^[\\\\w\\-_\\\\.+]*[\\\\w\\-_\\\\.]\\\\@([\\\\w]+\\\\.)+[\\\\w]+[\\\\w]$")
  @ApiModelProperty(value = "Email address", dataType = "String", example = "abc@a.com")
  @JsonProperty("emailID")
  private String emailID;

  @Column(name = "arrival_date", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonView(JsonViews.ShowDatesOnly.class)
  @ApiModelProperty(value = "Arrival date", notes = "If reservation is for 1 day then arrivalDate and departureDate should be same",
      example = "2019-12-20", dataType = "LocalDate")
  @JsonProperty("arrivalDate")
  private LocalDate arrivalDate;

  @Column(name = "departure_date", nullable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonView(JsonViews.ShowDatesOnly.class)
  @ApiModelProperty(value = "Departure date", example = "2019-12-20", dataType = "LocalDate")
  @JsonProperty("departureDate")
  private LocalDate departureDate;

  @ManyToOne(targetEntity = Campsite.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "campsite_id", referencedColumnName = "id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  @JsonProperty("campsite")
  @ApiModelProperty(value = "Campsite id", example = "1", dataType = "Long")
  private Campsite campsite;
}
