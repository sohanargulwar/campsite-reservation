package com.internal.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"reservationUUID", "successMessage", "errorMessage"})
@ApiModel
public class Response implements Serializable{

  @ApiModelProperty(value = "reservationUUID", example = "c4284e15-b22a-4cfb-8493-2ed266464b53", dataType = "String")
  @JsonProperty("reservationUUID")
  private String reservationUUID;

  @ApiModelProperty(value = "errorMessage", example = "The reservation dates conflicts.", dataType = "String")
  @JsonProperty("errorMessage")
  private String errorMessage;

  @ApiModelProperty(value = "successMessage", example = "The reservation is modified successfully.", dataType = "String")
  @JsonProperty("successMessage")
  private String successMessage;
}
