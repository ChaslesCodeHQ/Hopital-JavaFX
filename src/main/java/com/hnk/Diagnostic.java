package com.hnk;

public class Diagnostic {

    private int id;
    private int idConsultation;
    private String diagnosticPrincipal;
    private String diagnosticsSecondaires;
    private String niveauGravite;
    private String commentairesMedecin;

    public Diagnostic() {}

    public Diagnostic(int idConsultation,
                      String diagnosticPrincipal,
                      String diagnosticsSecondaires,
                      String niveauGravite,
                      String commentairesMedecin) {
        this.idConsultation = idConsultation;
        this.diagnosticPrincipal = diagnosticPrincipal;
        this.diagnosticsSecondaires = diagnosticsSecondaires;
        this.niveauGravite = niveauGravite;
        this.commentairesMedecin = commentairesMedecin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdConsultation() { return idConsultation; }
    public void setIdConsultation(int idConsultation) { this.idConsultation = idConsultation; }

    public String getDiagnosticPrincipal() { return diagnosticPrincipal; }
    public void setDiagnosticPrincipal(String diagnosticPrincipal) { this.diagnosticPrincipal = diagnosticPrincipal; }

    public String getDiagnosticsSecondaires() { return diagnosticsSecondaires; }
    public void setDiagnosticsSecondaires(String diagnosticsSecondaires) { this.diagnosticsSecondaires = diagnosticsSecondaires; }

    public String getNiveauGravite() { return niveauGravite; }
    public void setNiveauGravite(String niveauGravite) { this.niveauGravite = niveauGravite; }

    public String getCommentairesMedecin() { return commentairesMedecin; }
    public void setCommentairesMedecin(String commentairesMedecin) { this.commentairesMedecin = commentairesMedecin; }
}
