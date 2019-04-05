using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace NovelWriter
{
    /// <summary>
    /// Логика взаимодействия для MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        // Global variables
        string DBAddress;
        SQLiteConnection connection;
        // Global variables


        public MainWindow() => InitializeComponent();


        //On Load - get DB address
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            DBAddress = Environment.CurrentDirectory;
            DBAddress = DBAddress.Substring(0, DBAddress.IndexOf(@"\NovelWriter")) + @"\My Life Novel\app\src\main\assets\MyLifeNovel.db";
            connection = new SQLiteConnection(string.Format("Data Source={0};", DBAddress));
            initComponents();
        }

        private void initComponents()
        {
            //throw new NotImplementedException();
        }

        private void Update_DB_Btn_Click(object sender, RoutedEventArgs e)
        {
            throw new NotImplementedException();
        }


        private void NumberValidationTextBox(object sender, TextCompositionEventArgs e)
        {
            Regex regex = new Regex("[^0-9]+");
            e.Handled = regex.IsMatch(e.Text);
        }



        private void Text_Output_TB_MouseDoubleClick(object sender, MouseButtonEventArgs e)
        {
            Text_Output_TB.Text = "";
        }
    }
}
