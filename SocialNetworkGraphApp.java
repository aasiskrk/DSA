import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

// Define a class for representing a person in the social network
class Person {
    private int x;
    private int y;
    private int radius;
    private String name;
    private int followers;
    private Image image;

    public Person(int x, int y, int radius, String name, int followers, Image image) {
        // Constructor to initialize person properties
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.name = name;
        this.followers = followers;
        this.image = image;
    }

    // Getter and setter methods for person properties
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public String getName() {
        return name;
    }

    public int getFollowers() {
        return followers;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}

// Class representing an edge between two people
class Edge {
    private Person from;
    private Person to;
    private boolean streak;
    private int streakValue;

    public Edge(Person from, Person to) {
        // Constructor to initialize edge properties
        this.from = from;
        this.to = to;
        this.streak = false;
        this.streakValue = 0;
    }

    // Getter and setter methods for edge properties
    public Person getFrom() {
        return from;
    }

    public Person getTo() {
        return to;
    }

    public boolean hasStreak() {
        return streak;
    }

    public void setStreak(boolean streak) {
        this.streak = streak;
    }

    public int getStreakValue() {
        return streakValue;
    }

    public void setStreakValue(int streakValue) {
        this.streakValue = streakValue;
    }
}

// the main application class that extends JFrame
public class SocialNetworkGraphApp extends JFrame {
    private JPanel canvas;
    private JButton addButton;
    private JButton deleteButton;
    private JToggleButton edgeToggleButton;
    private JTextField searchField; // Search bar for user name
    private JButton searchButton; // Button to perform the search
    private ArrayList<Person> people;
    private ArrayList<Edge> edges;
    private Person selectedPerson;
    private Person fromPerson;
    private boolean connecting;
    private Random random;

    public SocialNetworkGraphApp() {
        // Initialize application components and properties
        random = new Random();
        people = new ArrayList<>();
        edges = new ArrayList<>();
        connecting = false;

        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw edges between people
                for (Edge edge : edges) {
                    Person from = edge.getFrom();
                    Person to = edge.getTo();
                    g.setColor(Color.BLACK);
                    g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
                    if (edge.hasStreak()) {
                        g.setColor(Color.RED);
                        g.drawLine(from.getX(), from.getY(), to.getX(), to.getY());
                        int centerX = (from.getX() + to.getX()) / 2;
                        int centerY = (from.getY() + to.getY()) / 2;
                        String streakValue = String.valueOf(edge.getStreakValue());
                        g.drawString(streakValue, centerX, centerY);
                    }
                }
                // Draw people nodes and their information
                for (Person person : people) {
                    Image personImage = person.getImage();
                    g.drawImage(personImage, person.getX() - person.getRadius(), person.getY() - person.getRadius(),
                            person.getRadius() * 2, person.getRadius() * 2, this);

                    g.setColor(Color.BLACK);
                    g.drawString(person.getName() + " (" + person.getFollowers() + " followers)",
                            person.getX() - person.getRadius(), person.getY() + person.getRadius() + 15);

                    if (person == selectedPerson) {
                        g.setColor(Color.RED);
                        g.drawOval(person.getX() - person.getRadius(), person.getY() - person.getRadius(),
                                person.getRadius() * 2, person.getRadius() * 2);
                    }
                }
            }
        };

        // Initialize buttons and action listeners
        addButton = new JButton("Add Person");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display a dialog to input person details
                JTextField nameField = new JTextField(10);
                JTextField followersField = new JTextField(10);
                JTextField imagePathField = new JTextField(20);

                JPanel inputPanel = new JPanel();
                inputPanel.setLayout(new GridLayout(4, 2));
                inputPanel.add(new JLabel("Name:"));
                inputPanel.add(nameField);
                inputPanel.add(new JLabel("Followers:"));
                inputPanel.add(followersField);
                inputPanel.add(new JLabel("Image Path:"));
                inputPanel.add(imagePathField);

                int result = JOptionPane.showConfirmDialog(
                        SocialNetworkGraphApp.this,
                        inputPanel,
                        "Enter Name, Followers, and Image Path",
                        JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    String name = nameField.getText();
                    int followers = Integer.parseInt(followersField.getText());
                    String imagePath = imagePathField.getText();

                    Image image = null;
                    try {
                        image = ImageIO.read(new File(imagePath)); // Load image based on provided path
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(SocialNetworkGraphApp.this, "Failed to load image.");
                        return;
                    }

                    int x = (int) (Math.random() * canvas.getWidth());
                    int y = (int) (Math.random() * canvas.getHeight());
                    int radius = 30;
                    people.add(new Person(x, y, radius, name, followers, image));
                    canvas.repaint();
                }
            }
        });

        deleteButton = new JButton("Delete Selected");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedPerson != null) {
                    // Delete the selected person and associated edges
                    people.remove(selectedPerson);
                    edges.removeIf(edge -> edge.getFrom() == selectedPerson || edge.getTo() == selectedPerson);
                    selectedPerson = null;
                    canvas.repaint();
                }
            }
        });

        edgeToggleButton = new JToggleButton("Edge");
        edgeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle edge creation mode
                connecting = edgeToggleButton.isSelected();
                if (!connecting) {
                    fromPerson = null;
                }
            }
        });

        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Search for a person by name
                String searchTerm = searchField.getText().toLowerCase();
                selectedPerson = null;

                for (Person person : people) {
                    if (person.getName().toLowerCase().contains(searchTerm)) {
                        selectedPerson = person;
                        canvas.repaint();
                        break; // Exit the loop once a match is found
                    }
                }
            }
        });

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (connecting) {
                    // Handle mouse clicks on canvas
                    if (fromPerson == null) {
                        // Select the starting person for edge creation
                        for (Person person : people) {
                            int distanceSquared = (e.getX() - person.getX()) * (e.getX() - person.getX()) +
                                    (e.getY() - person.getY()) * (e.getY() - person.getY());
                            int radiusSquared = person.getRadius() * person.getRadius();
                            if (distanceSquared <= radiusSquared) {
                                fromPerson = person;
                                edgeToggleButton.setSelected(false);
                                break;
                            }
                        }
                    } else {
                        // Select the ending person for edge creation
                        for (Person person : people) {
                            int distanceSquared = (e.getX() - person.getX()) * (e.getX() - person.getX()) +
                                    (e.getY() - person.getY()) * (e.getY() - person.getY());
                            int radiusSquared = person.getRadius() * person.getRadius();
                            if (distanceSquared <= radiusSquared && person != fromPerson) {
                                Edge newEdge = new Edge(fromPerson, person);
                                edges.add(newEdge);
                                String streakValue = JOptionPane.showInputDialog(
                                        SocialNetworkGraphApp.this,
                                        "Enter the streak value:",
                                        "Streak Value",
                                        JOptionPane.QUESTION_MESSAGE);
                                try {
                                    int streak = Integer.parseInt(streakValue);
                                    newEdge.setStreak(true);
                                    canvas.repaint();
                                    connecting = false;
                                    fromPerson = null;
                                    edgeToggleButton.setSelected(false);
                                    newEdge.setStreakValue(streak);
                                    break;
                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(SocialNetworkGraphApp.this,
                                            "Invalid streak value. Please enter a valid number.");
                                }
                            }
                        }
                    }
                } else {
                    // Select a person when not in edge creation mode
                    for (int i = people.size() - 1; i >= 0; i--) {
                        Person person = people.get(i);
                        int distanceSquared = (e.getX() - person.getX()) * (e.getX() - person.getX()) +
                                (e.getY() - person.getY()) * (e.getY() - person.getY());
                        int radiusSquared = person.getRadius() * person.getRadius();
                        if (distanceSquared <= radiusSquared) {
                            selectedPerson = person;
                            canvas.repaint();
                            break;
                        }
                    }
                }
            }
        });

        canvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            // Move the selected person when dragging
            public void mouseDragged(MouseEvent e) {
                if (selectedPerson != null) {
                    selectedPerson.setX(e.getX());
                    selectedPerson.setY(e.getY());
                    canvas.repaint();
                }
            }
        });
        setLayout(new BorderLayout());

        // Create the top panel with buttons and search functionality
        JPanel topPanel = new JPanel();
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        topPanel.add(edgeToggleButton);
        topPanel.add(searchField);
        topPanel.add(searchButton);
        add(topPanel, BorderLayout.NORTH);
        add(canvas, BorderLayout.CENTER);

        setTitle("Social Network Graph App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Main application entry point
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SocialNetworkGraphApp();
            }
        });
    }
}
