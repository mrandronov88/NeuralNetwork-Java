# NeuralNetwork-Java
This is a Java implementation of a basic neural network to read handwritten numbers from the MNIST data set. The training and test data are stored in a zip file under data/data.zip as .csv files for easy reading. In actually running the program, will need to extract data files from the zip archive and keep them in data/.

Outside of Java's standard libraries, I entirely coded all necessary functions myself, including matrix multiplication routines which are all kept in the same class as the NeuralNetwork class ( Although this should probably be stored in another file/class for better organization and future use ).

The network uses random values as a starting point and is trained iteratively against them. This means that you can train the network multiple times and get different overall accuracy as a result after going through the data. I have saved an iteration where ~91% accuracy was achieved, by having the program copy down import array values into a text file. 
