Project 1 - Amazon Review and Q&A Search
===========================================

### Due - Thursday, September 16, 2021 - 9:55am

For this project, you will implement a program to allow searching of a database of Amazon reviews and Q&A content. You will practice using the following:

- Efficient data structures
- JSON parsing
- Command line input/output
- Object-oriented design

## Requirements

### Setup

Download the [5-core Cell Phones and Accessories review](http://snap.stanford.edu/data/amazon/productGraph/categoryFiles/reviews_Cell_Phones_and_Accessories_5.json.gz) dataset and the [Cell Phones and Accessories Q&A](http://jmcauley.ucsd.edu/data/amazon/qa/qa_Cell_Phones_and_Accessories.json.gz) dataset from [Julian McAuley, UCSD](http://jmcauley.ucsd.edu/data/amazon/). During interactive grading you will be expected to demonstrate your solution working on these datasets, which are large and will require you to think about efficiency! Unzip the files and put the json source in your top-level project directory.

### InvertedIndex

You will create an `InvertedIndex` data structure and use *two* instances of it. The first will hold the data from the review dataset described above, and the second will hold the data from the Q&A dataset described above. 

An [inverted index](https://en.wikipedia.org/wiki/Inverted_index) (also see [this page](https://nlp.stanford.edu/IR-book/html/htmledition/a-first-take-at-building-an-inverted-index-1.html)) is a common data structure used for searching documents. It maps terms to the documents where those terms appear. This makes it easy to search for a term and get back a list of documents that contain the word.

For full credit, you must carefully design your `InvertedIndex` so that it can be easily used for both the 
review and the Q&A data.

### Indexing 

The goal of the `InvertedIndex` is to support searching of text. For the review dataset you will index the text in the field `reviewText`. For the Q&A dataset you will index both the `question` and `answer` fields. This will allow you to search for a term and get a list of reviews where the review text contains the term and a list of Q&A results where the question or answer contains the term.

The terms you use in your index must be only alphanumeric characters (letters or digits) and be case insensitive. This means that as you build the index you will need to separate the words in the text by whitespace, then remove all non-alphanumeric characters and convert the string to lower case.

Suppose you have two documents:

```
doc1 - "The cat and the dog ran."

doc2 - "Dog1 and dog2 ran the course."
```

Your inverted index would look as follows:

```
the -> doc1, doc2
cat -> doc1
and -> doc1, doc2
dog -> doc1
ran -> doc1, doc2
dog1 -> doc2
dog2 -> doc2
course -> doc2
```

Notice that "The" and "the" are the same, the "." has been removed from the end of "ran", and "dog1" differs from "dog" and "dog2".

You are also welcome to use additional data structures to make it more efficient to access elements of your data.

### UI

Your program will use a command line interface to allow the user to perform the following actions. Note that your program must continue to accept and process commands from the user until the user chooses to exit.

| Command | Functionality |
| ------ | -------- |  
| `find <asin>` | Given the ASIN number of a specific product, display a list of all reviews for that product and display a list of all questions and answers about that product. |  
| `reviewsearch <term>` | Given a one-word term, display a list of all reviews that contain the exact term. For full credit, the results must be sorted as described in Searching below. |  
| `qasearch <term>` | Given a one-word term, list of all question/answer documents that have the exact term in the question or answer. For full credit, the results must be sorted as described in Searching below. |  
| `reviewpartialsearch <term>` | Given a one-word term, display a list of all reviews where any word in the review contains a partial match to the term. A partial match is defined as the sequence of characters of the term appearing anywhere in the word, for example `he` would match `he`, `help`, and `then`. You may use your own sorting algorithm for displaying the results. |  
| `qapartialsearch <term>` | Given a one-word term, display a list of question/answer documents where any word in the question or answer contains a partial match to the term. A partial match is defined as the sequence of characters of the term appearing anywhere in the word, for example `he` would match `he`, `help`, and `then`. You may use your own sorting algorithm for displaying the results. |  

### Searching

For the exact search function (`reviewsearch <term>` or `qasearch <term>` command), your results must be sorted by [term frequency](https://nlp.stanford.edu/IR-book/html/htmledition/term-frequency-and-weighting-1.html). The result that appears first will be the result where the term appears most often in the document. In the example above, a search for "the" would return document 1 first, followed by document 2. This is because the term appears twice in document 1 and only once in document 2.   

You are *not* required to follow this algorithm for the partial search.

### Program Execution

Your main class is required to have the name `cs601.project1.AmazonSearch`.

Your main class will accept the following command line arguments: `-reviews <review_file_name> -qa <qa_file_name>`

You must exit the program if the arguments are not provided exactly as specified.

You must submit a jar file containing all classes and dependencies required to execute your program. The jar file *must* be in the top-level github repository and *must* be named `project1.jar`.

After cloning your github repository we will execute your program from the top-level of the repository using the following command, where the correct file names will be specified:

`java -cp project1.jar cs601.project1.AmazonSearch -reviews <review_file_name> -qa <qa_file_name>` 


### External Libraries

The only external libraries you may use for this assignment are [GSON](https://github.com/google/gson) 2.8.8 and JUnit. It is not required that you use JUnit for unit testing (but you should make sure you test your code). The `pom.xml` file provided in the starter code references all of the dependencies you are allowed to use for this assignment.

## Hints

1. Make sure to specify the `Charset` as "ISO-8859-1" when opening the input files.
2. Practice good design! Part of your grade will depend upon the design of your solution and code style. 

## Submission

1. Use the following link to create your private github repository for this assignment: [Project 1](https://classroom.github.com/a/kAZNqBSw)
2. For full credit, make sure to follow all [Style Guidelines](https://github.com/CS601-F21/notes/blob/main/admin/style.md). Points will be deducted for each violation.
3. All code and the jar file described above must be submitted to your github repository by **Thursday, September 16, 2021 - 9:55am**. Failure to submit the project as required will result in a score of 0.
4. Interactive grading is required for this project. A signup form will be made available prior to the deadline. You **must** be present for your interactive grading session on or before the day the project is due. Failure to demonstrate the functionality described below to the instructor or a TA will result in a score of 0.
5. There are no unit test cases provided for this project, but during interactive grading you will be expected to demonstrate your solution running on the Cell Phones and Accessories data. Make sure to test incorrect commands and searches for ASINs and terms that are not present in the data.

## Extra Credit

You may earn up to three (3) points of extra credit by completing interactive grading and submitting all required code during the professor's office hours on or before Wednesday, September 15, 2021. Note that office hours are first come, first served and *students with assignment questions have priority over students wishing to complete early interactive grading*. To earn the extra credit it is suggested you come for interactive grading well before the deadline!

## Grading Rubric

| Points | Category | Criterion Description |
| ------ | -------- | --------------------- |
| 10 | **Functionality** | `find` |  
| 10 | **Functionality** | `reviewsearch ` |  
| 10 | **Functionality** | `qasearch ` |  
| 10 | **Functionality** | `reviewpartialsearch ` |  
| 10 | **Functionality** | `qapartialsearch ` |  
| 5 | **Functionality** | `error conditions - program execution ` |  
| 5 | **Functionality** | `error conditions - search/find inputs ` | 
| 5 | **Design** | `InvertedIndex` design follows the principle of encapsulation. |  
| 5 | **Design** | User interface functionality is decoupled from data. |  
| 10 | **Design** | Solution uses appropriate class and method decomposition. |  
| 10 | **Design** | Algorithm for sorting search results is implemented correctly and efficiently. |  
| 10 | **Design** | Meets all style guidelines. |  

## Academic Dishonesty

Any work you submit is expected to be your own original work. If you use any web resources in developing your code you are strongly advised to cite those resources. The only exception to this rule is code that is posted on the class website. The URL of the resource you used in a comment in your code is fine. If I google even a single line of uncited code and find it on the internet you may get a 0 on the assignment or an F in the class. You may also get a 0 on the assignment or an F in the class if your solution is at all similar to that of any other student.

