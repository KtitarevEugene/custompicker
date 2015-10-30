// This is a test harness for your module
// You should do something interesting in this harness
// to test out the module and to provide instructions
// to users on how to use it by example.


// open a single window
var win = Ti.UI.createWindow({
	backgroundColor:'white'
});
var label = Ti.UI.createLabel();
win.add(label);
win.open();

// module test
var custompicker = require('com.takemeoutto.custompicker'),
    picker;

if (OS_ANDROID) {
	picker = custompicker.createCustomPicker({
		width: 100,
		height: 100,
		top: 100,
		left: 150,
        backgroundColor: "white",
        format24: true,                 // 24-hour time format 
        dividersColor: 'transparent',   // dividers color
        foregroundColor: 'black',       // foreground color
        innerPadding: -50,              // padding between hours and minutes pickers,
                                        // positive (negative) value decreases (increases)
                                        // padding on given value from default one
        separatorSize: 28.0             // colon separator size
	}); // necessary 

	win.add(picker);
}
