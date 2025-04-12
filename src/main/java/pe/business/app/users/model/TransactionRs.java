package pe.business.app.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TransactionRs<T> {
	@JsonProperty("success")
	private Boolean success=false;
	
	@JsonProperty("mensaje")
	private String descripcion="Ocurrio un error";
	
	@JsonProperty("detRpta")
	@JsonInclude(Include.NON_NULL)
	private T respuesta;
	
	public void isSuccess(){
		success=true;
		descripcion="Respuesta Exitosa";
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public T getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(T respuesta) {
		this.respuesta = respuesta;
	}
}
