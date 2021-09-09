/*
 Author Name : Shubham Pareek
 Class function : Blueprint of a QuestionAnswer object and everything that object contains
 Project Number : 1
*/

package cs601.project1;

public class QuestionAnswer {
    private String questionType;
    private String asin;
    private String answerTime;
    private String unixTime;
    private String question;
    private String answerType;
    private String answer;

    public QuestionAnswer(String questionType, String asin, String answerTime, String unixTime, String question, String answerType, String answer) {
        this.questionType = questionType;
        this.asin = asin;
        this.answerTime = answerTime;
        this.unixTime = unixTime;
        this.question = question;
        this.answerType = answerType;
        this.answer = answer;
    }


    public String getQuestionType() {
        return questionType;
    }

    public String getAsin() {
        return asin;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public String getUnixTime() {
        return unixTime;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerType() {
        return answerType;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        if (answerType == null){
            return "QuestionAnswer{" +
                    "questionType='" + questionType + '\'' +
                    ", asin='" + asin + '\'' +
                    ", answerTime='" + answerTime + '\'' +
                    ", unixTime='" + unixTime + '\'' +
                    ", question='" + question + '\'' +
                    ", answer='" + answer + '\'' +
                    '}';
        }
        return "QuestionAnswer{" +
                "questionType='" + questionType + '\'' +
                ", asin='" + asin + '\'' +
                ", answerTime='" + answerTime + '\'' +
                ", unixTime='" + unixTime + '\'' +
                ", question='" + question + '\'' +
                ", answerType='" + answerType + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
