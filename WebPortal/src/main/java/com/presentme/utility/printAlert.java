package com.presentme.utility;

public class printAlert {

    public static String getAlertBox(String title, String message) {

        String modal = "<!-- Logout Modal-->\n" +
                "<div class=\"modal fade\" id=\"AlertModal\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\n" +
                "    <div class=\"modal-dialog\" role=\"document\">\n" +
                "        <div class=\"modal-content\">\n" +
                "            <div class=\"modal-header\">\n" +
                "                <h5 class=\"modal-title\" id=\"exampleModalLabel\">" + title + "</h5>\n" +
                "                <button style=\"margin-top: -25px\" class=\"close\" type=\"button\" data-dismiss=\"modal\" aria-label=\"Close\">\n" +
                "                    <span aria-hidden=\"true\">x</span>\n" +
                "                </button>\n" +
                "            </div>\n" +
                "            <div class=\"modal-body\">" + message + "</div>\n" +
                "            <div class=\"modal-footer\">\n" +
                "                <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">Close</button>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
        String js = "<script>\n" +
                "        $('#AlertModal').modal('show')\n" +
                "    </script>";

        return modal + js;
    }

    public static String getSubmitBox(String id, String title, String content) {

        return "<div class=\"modal fade\" id=\"" + id + "\" tabindex=\"-1\" role=\"dialog\"\n" +
                "                                 aria-labelledby=\"exampleModalLabel\"\n" +
                "                                 aria-hidden=\"true\">\n" +
                "                                <div class=\"modal-dialog\" role=\"document\">\n" +
                "                                    <div class=\"modal-content\">\n" +
                "                                        <div class=\"modal-header\">\n" +
                "                                            <h5 class=\"modal-title\" id=\"exampleModalLabel\">" + title + "</h5>\n" +
                "                                            <button style=\"margin-top: -25px\" class=\"close\" type=\"button\"\n" +
                "                                                    data-dismiss=\"modal\"\n" +
                "                                                    aria-label=\"Close\">\n" +
                "                                                <span aria-hidden=\"true\">x</span>\n" +
                "                                            </button>\n" +
                "                                        </div>\n" +
                "                                        <div class=\"modal-body\">" + content + "\n" +
                "                                        </div>\n" +
                "                                        <div class=\"modal-footer\">\n" +
                "                                            <button class=\"btn btn-secondary\" type=\"button\" data-dismiss=\"modal\">\n" +
                "                                                Cancel\n" +
                "                                            </button>\n" +
                "                                            <input type=\"submit\" value=\"Confirm\" class=\"btn btn-primary\"/>\n" +
                "                                        </div>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </div>";
    }
}
