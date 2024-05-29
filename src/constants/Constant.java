package constants;

public class Constant {
    public static class MainFrame
    {
        public final static long VERSIONID = 1L;
        public final static int X = 200;
        public final static int Y = 100;
        public final static int WIDTH = 600;
        public final static int HEIGHT = 400;

    }
    public static class IndexTable
    {
        public enum EHeader
        {
            eID("아이디"),
            eName("이름");

            public String title;
            private EHeader(String title)
            {
                this.title = title;
            }
            public String getTitle()
            {
                return this.title;
            }


        }

    }
}
