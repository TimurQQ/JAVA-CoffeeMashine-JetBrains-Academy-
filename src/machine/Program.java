package machine;

import java.util.Scanner;
import java.lang.Math;

class CoffeeMachine {
    
    public static class Resource {
	    public String unit;
	    public String resource_type;
	    public int count;
	    
	    public Resource(String unit, String resource_type, int count) {
	    	this.unit = unit;
	    	this.resource_type = resource_type;
	    	this.count = count;
	    }
	}
    
    enum State {
    	WAIT, MAKE_COFFEE, FILLING
    }
    
    private static State state = State.WAIT;
    
    private static int res_index = 0;
    
    private static Resource[] resources = {new Resource("ml of ", "water", 400),
    		new Resource("ml of ", "milk", 540), new Resource("grams of ", "coffee beans", 120),
            new Resource("", "disposable cups", 9), new Resource("$ " ,"money", 550)};
    
    public static void pushButton(String name) {
        switch (name) {
            case "buy":
                System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
                state = State.MAKE_COFFEE;
                break;
            case "back":
            	System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            	state = State.WAIT;
            	break;
            case "fill":
            	String units = resources[res_index].unit;
		        String resource = resources[res_index].resource_type;
		        System.out.printf("Write how many %s%s do you want to add:\n", units, resource);
            	state = State.FILLING;
                break;
            case "take":
                System.out.printf("I gave you $%d\n", resources[resources.length - 1].count);
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                resources[resources.length - 1].count = 0;
                break;
            case "remaining":
                System.out.println("The coffee machine has:");
                for (int i = 0; i < resources.length; ++i) {
                    printParam(i);
                }
                System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
                break;
            case "exit":
                break;
            default:
            	if (isNumeric(name)) {
            		switch (state) {
            			case WAIT:
            				break;
            			case MAKE_COFFEE:
            				switch (name) {
		            			case "1":
			                        int[] purchase = new int[] {-250, -0, -16, -1, 4};
			                        createTransaction(purchase);
			                        break;
			                    case "2":
			                        purchase = new int[] {-350, -75, -20, -1, 7};
			                        createTransaction(purchase);
			                        break;
			                    case "3":
			                        purchase = new int[] {-200, -100, -12, -1, 6};
			                        createTransaction(purchase);
			                        break;
			                    default:
			                    	state = State.WAIT;
			                    	break;
            				}
            				System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            				break;
            			case FILLING:
            				int d = Integer.parseInt(name);
            				resources[res_index].count += d;
            				if (++res_index > 3) {
            					res_index = 0;
            					System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            					state = State.WAIT;
            				}
            				else {
            					units = resources[res_index].unit;
            			        resource = resources[res_index].resource_type;
            			        System.out.printf("Write how many %s%s do you want to add:\n", units, resource);
            				}
            				break;
            		}
            	}
            	else {
            		System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
            	}
            	break;
        }
    }
    
    public static boolean isNumeric(String strNum) {
        if (strNum == null) return false;
        try {
            int d = Integer.parseInt(strNum);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public static void createTransaction(int[] purchase) {
    	int index = isEnoughResources(purchase);
    	if (index == -1) {
    		System.out.println("I have enough resources, making you a coffee!");
    		for (int i = 0; i < resources.length; ++i) {
                resources[i].count += purchase[i];
            }
    	}
    	else {
    		System.out.printf("Sorry, not enough %s!\n", resources[index].resource_type);
    	}
    }
    
    public static int isEnoughResources(int[] purchase) {
    	for (int i = 0; i < 4; ++i) {
    		if (-purchase[i] > resources[i].count) {
    			return i;
    		}
    	}
    	return -1;
    }
    
    public static void printParam(int index) {
        int count = resources[index].count;
        String resource = resources[index].resource_type;
        System.out.printf("%d of %s\n", count, resource);
    }
}

class CoffeeMachineDemo {
    public static void main(String[] args) {
    	Scanner console = new Scanner(System.in);
    	System.out.println("Write action (buy, fill, take, remaining, exit):");
    	while(true) {
            String btn = console.nextLine();
            CoffeeMachine.pushButton(btn);
            if (btn.equals("exit")) break;
    	}
    }
}
