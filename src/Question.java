class Question {
    private Integer index;
    private String problem;
    private String answer;

    public Question(Integer index, String problem, String answer) {
        if (index == null || problem == null || answer == null) {
            throw new NullPointerException();
        }

        this.index = index;
        this.problem = problem;
        this.answer = answer;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getProblem() {
        return this.problem;
    }

    public String getAnswer() {
        return this.answer;
    }
}