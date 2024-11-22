import java.util.Scanner;
public class Main {
       
     public static Scanner input = new Scanner (System.in);
     public static SearchEngine SE = new SearchEngine();
    
    public static int menu()
    {
        System.out.println("1. Boolean Retrieval. ");
        System.out.println("2. Ranked Retrieval.");
        System.out.println("3. Indexed Documents.");
        System.out.println("4. Indexed Tokens.");
        System.out.println("5. Exist.");
        
        System.out.println("enter choice");
        int choice = input.nextInt();
        return choice;
    }

    
    public static void Boolean_Retrieval_menu()
    {
        String [] Questions = { "market AND sports"
                                             ,"weather AND warming"   
                                            ,"business AND world"
                                            ,"weather OR warming"
                                            ,"market OR sports"
                                            ,"market OR sports AND warming"};

        System.out.println("################### Boolean Retrieval ####################");
        System.out.print("Q#: ");
        
        for ( int i = 0 ; i < Questions.length; i++)
        {
            String str = Questions[i];

            System.out.println(str);
            System.out.print("Result doc IDs: ");
            SE.Boolean_Retrieval(str, 2 ).print();
            System.out.println("\n");
        }
    }

    public static void Ranked_Retrieval_menu()
    {
        String [] Questions = { "market sports"
                                             ,"weather warming"   
                                            ,"business world market"};

        System.out.println("######## Ranked Retrieval ######## ");
        System.out.print("Q#: ");
        
        for ( int i = 0 ; i < Questions.length; i++)
        {
            String str = Questions[i];
 
            System.out.println("## Q: " + str);
            System.out.println("DocIDt\tScore");
            SE.Ranked_Retrieval(str);
            System.out.println("\n");
        }
    }
    
    public static void Indexed_Documents_menu()
    {
        System.out.println("######## Indexed Documents ######## ");
        System.out.println("Indexed Documents " + SE.Index.indexes.length);
    }
    
    public static void Indexed_Tokens_menu()
    {
        System.out.println("######## Indexed Tokens ######## ");
        System.out.println("tokens " + SE.token);
    }
    
    public static void main(String[] args) {

        SE.readStopWords("stop.txt");
        SE.readDocument("dataset.csv");


        int choice;
        
        do {
                choice = menu();
                switch (choice)
                {
                    case 1:
                            Boolean_Retrieval_menu();
                            break;
                            
                    case 2:
                            Ranked_Retrieval_menu();
                            break;
                    
                    case 3:
                            Indexed_Documents_menu();
                            break;
                    
                    case 4:
                            Indexed_Tokens_menu();
                            break;
                     
                    case 5:
                            break;
                            
                    default:       
                            System.out.println("bad choice, try again!");
                }
        } while (choice != 5);
    }
    
}
