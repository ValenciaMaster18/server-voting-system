package com.senatic.servervotingsystem.model.entity.enums;


public enum EstadoAprendiz {

    EN_FORMACION("EN FORMACION"),
    CANCELADO("CANCELADO"),
    RETIRADO("RETIRADO"),
    RETIRO_VOLUNTARIO("RETIRO VOLUNTARIO"),
    TRASLADADO("TRASLADADO"),
    SUSPENDIDO("SUSPENDIDO"),
    NOT_PROVIDED("NOT PROVIDED");

    private String plainText;

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    EstadoAprendiz(String plainText){
        this.plainText = plainText;
    } 

    public static EstadoAprendiz checkAndAssign(String status){
        EstadoAprendiz estadoAssigned = EstadoAprendiz.EN_FORMACION;
        for ( EstadoAprendiz estado : EstadoAprendiz.values()) {
            if (estado.plainText.equalsIgnoreCase(status)) {
                estadoAssigned = estado;
            }
        }
        return estadoAssigned;
    }

}
