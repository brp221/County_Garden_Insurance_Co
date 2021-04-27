# County_Garden_Insurance_Co


Instructions to Run Project: 
1st: From the Main directory compile Main.java. 
2nd: Move all the .class files from Main directory into brp221 directory
3rd: From the brp221 directory run this : "jar cfmv Main.jar Manifest.txt Main.class Policy.class" 
     Make sure that youve transferred all the necessary .class files Main uses
4th: Run  "java -jar Main.jar"

From Main: 
javac Main.java ; mv Main.class CorpManagement.class Adjuster.class Agent.class CustomerAction.class ../brp221/ ; 
cd ../brp221/ ; jar cfmv Main.jar Manifest.txt Main.class CorpManagement.class CustomerAction.class Adjuster.class Agent.class;
java -jar Main.jar 



