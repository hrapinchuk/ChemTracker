package model;

/**
 * This model class provides the ability to create and access TextInputInfo objects.
 * TextInputInfo objects store information pertaining to TextInputControl objects and are used to
 * perform data validations.
 * @author Heather Rapinchuk
 */
public class TextInputInfo {
    // Variable declarations
    private boolean required;
    private int maxLength;
    private String pattern;
    private String patternError;

    // Constructor
    /**
     * This constructor creates a TextInputInfo object.
     * @param required A boolean describing whether a value must be entered into the TextInputControl
     * @param maxLength An int representing the TextInputControl's value's maximum allowable length
     * @param pattern A String containing a regular expression, whose pattern must be matched by the
     *                value within the TextInputControl
     * @param patternError A String describing the pattern that must be matched by the value within
     *                     the TextInputControl
     */
    public TextInputInfo(boolean required,
                         int maxLength,
                         String pattern,
                         String patternError) {
        this.required = required;
        this.maxLength = maxLength;
        this.pattern = pattern;
        this.patternError = patternError;
    }

    // Methods
    /**
     * This method indicates whether a value is required for the TextInputControl.
     * @return A boolean describing whether a value is required for the TextInputControl
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * This method gets the maximum length allowed for the value within the TextInputControl.
     * @return An int representing the maximum length allowed for the value within the TextInputControl
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * This method gets the pattern that must be matched by the value within the TextInputControl.
     * @return A String containing a regular expression, whose pattern must be matched by the value
     * within the TextInputControl
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * This method gets a description of the pattern that must be matched by the value within the
     * TextInputControl.
     * @return A String describing the pattern that must be matched by the value within the
     * TextInputControl
     */
    public String getPatternError() {
        return patternError;
    }
}
