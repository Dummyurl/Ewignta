package ewingta.domesticlogistic.models;

public class PdfResponse {

        private String status;

        private String filename;

        private String url;

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        public String getFilename ()
        {
            return filename;
        }

        public void setFilename (String filename)
        {
            this.filename = filename;
        }

        public String getUrl ()
        {
            return url;
        }

        public void setUrl (String url)
        {
            this.url = url;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [status = "+status+", filename = "+filename+", url = "+url+"]";
        }

}
