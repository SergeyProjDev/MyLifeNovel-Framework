using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Text.RegularExpressions;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using Path = System.IO.Path;

namespace NovelWriter
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {

        // Global variables

        string DBAddress;  // "...\NovelFramework\My Life Novel\app\src\main\assets\MyLifeNovel.db"
        SQLiteConnection connection;

        Dictionary<int, string> music;
        Dictionary<int, string> bgs;
        Dictionary<int, string> names;
        Dictionary<int, string> sprites;





        public MainWindow() => InitializeComponent();



        //On Load - get DB address
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            DBAddress = Environment.CurrentDirectory;
            DBAddress = DBAddress.Substring(0, DBAddress.IndexOf(@"\NovelWriter")) + @"\My Life Novel\app\src\main\assets\MyLifeNovel.db";
            connection = new SQLiteConnection(string.Format("Data Source={0};", DBAddress));
            initComponents();
            Copy_Clean_UserResources();
        }



        private void initComponents()
        {
            music = getIntStrData("music");
            bgs = getIntStrData("backgrounds");
            names = getIntStrData("characters");
            sprites = getIntStrData("sprites");

            Music_CB.Items.Clear();
            Backgroung_CB.Items.Clear();
            Sprite_1.Items.Clear();
            Sprite_2.Items.Clear();
            Sprite_3.Items.Clear();
            Saying.Items.Clear();


            for (int i = 1; i <= music.Count; i++)
            {
                if (music[i] == "click") continue;
                Music_CB.Items.Add(music[i]);
            }
            for (int i = 1; i <= bgs.Count; i++)
                Backgroung_CB.Items.Add(bgs[i].Substring(11));
            for (int i = 1; i <= names.Count; i++)
            {
                if (!Saying.Items.Contains(names[i]))
                    Saying.Items.Add(names[i]);
            }
            for (int i = 1; i <= sprites.Count; i++)
            {
                if (sprites[i] != "")
                {
                    Sprite_1.Items.Add(sprites[i].Substring(7));
                    Sprite_2.Items.Add(sprites[i].Substring(7));
                    Sprite_3.Items.Add(sprites[i].Substring(7));
                }
            }


            Saying_CB.IsChecked = true;
            Sprite_1_CB.IsChecked = true;
            Sprite_2_CB.IsChecked = true;
            Sprite_3_CB.IsChecked = true;

            try
            {
                Music_CB.SelectedIndex = 0;
                Backgroung_CB.SelectedIndex = 0;
                Sprite_1.SelectedIndex = 0;
                Sprite_2.SelectedIndex = 0;
                Sprite_3.SelectedIndex = 0;
                Saying.SelectedIndex = 0;
            }
            catch (Exception) { }

            Dictionary<int, string> getIntStrData(string table)
            {
                connection.Open();
                string query = $"SELECT * FROM {table};";
                SQLiteCommand command = new SQLiteCommand(query, connection);
                SQLiteDataReader sqlDR = command.ExecuteReader();

                Dictionary<int, string> elements = new Dictionary<int, string>();

                while (sqlDR.Read())
                    elements.Add(
                        int.Parse(sqlDR.GetValue(0).ToString()), //id
                        sqlDR.GetValue(1).ToString() //name
                    );

                sqlDR.Close();
                connection.Close();

                return elements;
            }
        }



        private void Update_DB_Btn_Click(object sender, RoutedEventArgs e)
        {
            Copy_Clean_UserResources();

            string resursesAddres = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\";

            //clearing Dictionaries
            music.Clear();
            bgs.Clear();
            names.Clear();
            sprites.Clear();

            //reading backgrounds to db
            string[] backgrounds = Directory.GetFiles(resursesAddres + "drawable", "*.jpg").Select(System.IO.Path.GetFileName).ToArray();
            for (int i = 0; i < backgrounds.Length; i++)
            {
                if (Path.GetFileName(backgrounds[i]).Contains("background_"))
                {
                    connection.Open();
                    SQLiteCommand cmd = new SQLiteCommand(connection);
                    cmd.CommandText = "SELECT count(*) FROM backgrounds WHERE name='" + Path.GetFileNameWithoutExtension(backgrounds[i]) + "'";
                    if (Convert.ToInt32(cmd.ExecuteScalar()) == 0)
                    {
                        string query = "INSERT INTO backgrounds VALUES (null, \"" + backgrounds[i].Substring(0, backgrounds[i].Length - 4) + "\");";
                        SQLiteCommand insertSQL = new SQLiteCommand(query, connection);
                        insertSQL.ExecuteNonQuery();
                    }
                    connection.Close();
                }
            }

            //reading sprites to db
            string[] spritesPNG = Directory.GetFiles(resursesAddres + "drawable", "*.png").Select(Path.GetFileName).ToArray();
            for (int i = 0; i < spritesPNG.Length; i++)
            {
                if (Path.GetFileName(spritesPNG[i]).Contains("sprite_"))
                {
                    connection.Open();
                    SQLiteCommand cmd = new SQLiteCommand(connection);
                    cmd.CommandText = "SELECT count(*) FROM sprites WHERE link='" + Path.GetFileNameWithoutExtension(spritesPNG[i]) + "'";
                    if (Convert.ToInt32(cmd.ExecuteScalar()) == 0)
                    {
                        string query = "INSERT INTO sprites VALUES (null, \"" + spritesPNG[i].Substring(0, spritesPNG[i].Length - 4) + "\");";
                        SQLiteCommand insertSQL = new SQLiteCommand(query, connection);
                        insertSQL.ExecuteNonQuery();
                    }
                    connection.Close();
                }
            }

            //reading music to db
            string[] mus = Directory.GetFiles(resursesAddres + "raw", "*.mp3").Select(Path.GetFileName).ToArray();
            for (int i = 0; i < mus.Length; i++)
            {
                connection.Open();
                SQLiteCommand cmd = new SQLiteCommand(connection);
                cmd.CommandText = "SELECT count(*) FROM music WHERE name='" + Path.GetFileNameWithoutExtension(mus[i]) + "'";
                if (Convert.ToInt32(cmd.ExecuteScalar()) == 0)
                {
                    string query = "INSERT INTO music VALUES (null, \"" + mus[i].Substring(0, mus[i].Length - 4) + "\");";
                    SQLiteCommand insertSQL = new SQLiteCommand(query, connection);
                    insertSQL.ExecuteNonQuery();
                }
                connection.Close();
            }

            Window_Loaded(null, null);
        }



        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }



        private void Text_Output_TB_MouseDoubleClick(object sender, MouseButtonEventArgs e) => Text_Output_TB.Text = "";


        private void Sprite_1_CB_Checked(object sender, RoutedEventArgs e) => Sprite_1.IsEnabled = true;
        private void Sprite_1_CB_Unchecked(object sender, RoutedEventArgs e) => Sprite_1.IsEnabled = false;

        private void Sprite_2_CB_Checked(object sender, RoutedEventArgs e) => Sprite_2.IsEnabled = true;
        private void Sprite_2_CB_Unchecked(object sender, RoutedEventArgs e) => Sprite_2.IsEnabled = false;

        private void Sprite_3_CB_Checked(object sender, RoutedEventArgs e) => Sprite_3.IsEnabled = true;
        private void Sprite_3_CB_Unchecked(object sender, RoutedEventArgs e) => Sprite_3.IsEnabled = false;

        private void Saying_CB_Checked(object sender, RoutedEventArgs e) => Saying.IsEnabled = true;
        private void Saying_CB_Unchecked(object sender, RoutedEventArgs e) => Saying.IsEnabled = false;



        private void Copy_Clean_UserResources()
        {
            string userSources = DBAddress.Substring(0, DBAddress.IndexOf(@"\My Life Novel")) + @"\Sources\";
            string programSources = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\";



            string backgrounds = userSources + @"\Backgrounds";
            string drawable = programSources + @"\drawable";
            foreach (FileInfo f in new DirectoryInfo(backgrounds).GetFiles()) File.Move(f.FullName, f.FullName.Replace(f.Name, "background_" + f.Name));
            foreach (string sourcefile in Directory.GetFiles(backgrounds))
                try
                {
                    File.Move(sourcefile, Path.Combine(drawable, Path.GetFileName(sourcefile)));
                }
                catch (Exception ex) { }
                finally
                {
                    File.Delete(sourcefile);
                }



            string sprites = userSources + @"\Sprites";
            foreach (FileInfo f in new DirectoryInfo(sprites).GetFiles()) File.Move(f.FullName, f.FullName.Replace(f.Name, "sprite_" + f.Name));
            foreach (string sourcefile in Directory.GetFiles(sprites))
                try
                {
                    File.Move(sourcefile, Path.Combine(drawable, Path.GetFileName(sourcefile)));
                }
                catch (Exception ex) { }
                finally
                {
                    File.Delete(sourcefile);
                }



            string mus = userSources + @"\Music";
            string raw = programSources + @"\raw";
            foreach (string sourcefile in Directory.GetFiles(mus))
                try
                {
                    File.Move(sourcefile, Path.Combine(raw, Path.GetFileName(sourcefile)));
                }
                catch (Exception ex) { }
                finally
                {
                    File.Delete(sourcefile);
                }
        }



        private void Backgroung_CB_SelectionChanged(object sender, System.Windows.Controls.SelectionChangedEventArgs e)
        {
            try
            {
                string curAdr = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\drawable\background_" + (sender as ComboBox).SelectedItem.ToString() + ".jpg";
                Background_Output_Img.Source = new BitmapImage(new Uri(curAdr));
            }
            catch (Exception) { }
        }



        private void Sprite_1_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            try
            {
                string curAdr = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\drawable\sprite_" + (sender as ComboBox).SelectedItem.ToString() + ".png";
                Sprite_1_Output_Img.Source = new BitmapImage(new Uri(curAdr));
            }
            catch (Exception ex) { MessageBox.Show(ex.ToString()); }
        }
        private void Sprite_2_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            try
            {
                string curAdr = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\drawable\sprite_" + (sender as ComboBox).SelectedItem.ToString() + ".png";
                Sprite_2_Output_Img.Source = new BitmapImage(new Uri(curAdr));
            }
            catch (Exception) { }
        }
        private void Sprite_3_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            try
            {
                string curAdr = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\drawable\sprite_" + (sender as ComboBox).SelectedItem.ToString() + ".png";
                Sprite_3_Output_Img.Source = new BitmapImage(new Uri(curAdr));
            }
            catch (Exception) { }
        }



        private void Add_Characte_btn_Click(object sender, RoutedEventArgs e)
        {
            //add to db
            connection.Open();
            string query = $"INSERT INTO characters VALUES (null, \"{Add_Character_Name.Text}\");";
            SQLiteCommand command = new SQLiteCommand(query, connection);
            command.ExecuteNonQuery();
            connection.Close();

            //add to box
            Saying.Items.Add(Add_Character_Name.Text);

            //cls field
            Add_Character_Name.Clear();
        }



        private void Show_New_Character_Field_Click(object sender, RoutedEventArgs e)
        {
            if (!Add_Characte_btn.IsVisible)
            {
                Add_Characte_btn.Visibility = Visibility.Visible;
                Add_Character_Name.Visibility = Visibility.Visible;
            }
            else
            {
                Add_Characte_btn.Visibility = Visibility.Hidden;
                Add_Character_Name.Visibility = Visibility.Hidden;
            }

        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Window1 win2 = new Window1();
            win2.Show();
        }

        private void Add_Btn_Click(object sender, RoutedEventArgs e)
        {
            //choice
            int choice = int.Parse(Choice_Number_TB.Text);

            //music_id
            int? music_id = null;
            foreach (var key in from entry in music where entry.Value == Music_CB.Text select entry.Key)
                music_id = key;

            //background_id
            int backGR_id = -1;
            foreach (var key in from entry in bgs where entry.Value == "background_"+Backgroung_CB.Text select entry.Key)
                backGR_id = key;

            //sprites_id
            string drawSprites = "";
            if ((bool)Sprite_1_CB.IsChecked)
            {
                foreach (var key in from entry in sprites where entry.Value == "sprite_" + Sprite_1.Text select entry.Key)
                    drawSprites += key;
            }
            if ((bool)Sprite_2_CB.IsChecked)
            {
                foreach (var key in from entry in sprites where entry.Value == "sprite_" + Sprite_2.Text select entry.Key)
                    drawSprites += " " + key;
            }
            if ((bool)Sprite_3_CB.IsChecked)
            {
                foreach (var key in from entry in sprites where entry.Value == "sprite_" + Sprite_3.Text select entry.Key)
                    drawSprites += " " + key;
            }

            //saying_id
            int? saying_character_id = null;
            if ((bool)Saying_CB.IsChecked)
                foreach (var key in from entry in names where entry.Value == Saying.Text select entry.Key)
                    saying_character_id = key;

            //text content
            string text = Text_Input_TB.Text;

            AddToDatabase(choice, text, saying_character_id, drawSprites, music_id, backGR_id);
            AddToLog(Saying.Text, Text_Input_TB.Text);

            Text_Input_TB.Text = "";
        }

        void AddToDatabase(int choice, string text, int? saying_character_id, string drawSprites, int? music_id, int backGR_id)
        {
            if (backGR_id == -1) { MessageBox.Show("Bg = -1 Error"); return; }

            string[] value = new string[7];

            value[1] = choice.ToString();
            value[2] = "\"" + text + "\"";
            if (saying_character_id == null) value[3] = "null"; else value[3] = saying_character_id.ToString();
            if (drawSprites == "") value[4] = "null"; else value[4] = "\"" + drawSprites + "\"";
            if (music_id == null) MessageBox.Show("Music == null"); else value[5] = music_id.ToString();
            value[6] = backGR_id.ToString();

            string query = @"INSERT INTO day1 VALUES(
                                    null,
                                    " + value[1] + @",
                                    " + value[2] + @",
                                    " + value[3] + @",
                                    " + value[4] + @",
                                    " + value[5] + @",
                                    " + value[6] + @"
                                );";

            try { connection.Open(); } catch (Exception) { }

            SQLiteCommand insertSQL = new SQLiteCommand(query, connection);
            insertSQL.ExecuteNonQuery();
        }

        void AddToLog(string saying, string text)
        {
            if (saying != "") saying += ": ";
            if (!(bool)Saying_CB.IsChecked) saying = "";

            Text_Output_TB.Text += saying + text + Environment.NewLine;
        }

        private void Window_Closing(object sender, System.ComponentModel.CancelEventArgs e)
        {
            string drw = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets"))+@"\res\drawable";
            string raw = DBAddress.Substring(0, DBAddress.IndexOf(@"\assets")) + @"\res\raw";

            Delgitkeep(drw);
            Delgitkeep(raw);

            void Delgitkeep(string address)
            {
                DirectoryInfo di = new DirectoryInfo(drw);
                FileInfo[] files = di.GetFiles("*.gitkeep").Where(p => p.Extension == ".gitkeep").ToArray();
                foreach (FileInfo file in files)
                    try
                    {
                        file.Attributes = FileAttributes.Normal;
                        File.Delete(file.FullName);
                    }
                    catch (Exception ex){ MessageBox.Show(ex.ToString()); }
                File.Delete(address+@"\.gitkeep");
            }
        }
    }
}
