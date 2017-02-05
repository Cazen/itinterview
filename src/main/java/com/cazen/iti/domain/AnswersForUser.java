package com.cazen.iti.domain;

import java.io.Serializable;

/**
 * A RightAnswer.
 */
public class AnswersForUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String generatedId;

    private String optionText;

    public String getGeneratedId() {
        return generatedId;
    }

    public void setGeneratedId(String generatedId) {
        this.generatedId = generatedId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswersForUser that = (AnswersForUser) o;

        if (generatedId != null ? !generatedId.equals(that.generatedId) : that.generatedId != null) return false;
        return optionText != null ? optionText.equals(that.optionText) : that.optionText == null;

    }

    @Override
    public int hashCode() {
        int result = generatedId != null ? generatedId.hashCode() : 0;
        result = 31 * result + (optionText != null ? optionText.hashCode() : 0);
        return result;
    }
}
