# data-converter
An afternoon project (~1 hour) that I created out of boredom. It's a tool running in the background which allows you to convert between different numeric bases and binary encodings. Supported numeric bases are: binary, hexadecimal, octal, decimal. Supported encodings are: XS-3 (Excess 3), BCD, GrayCode. All, except gray code, support floating point numbers (using comma (,) separator for decimal places).

# Usage
When the tool is started there is no indication of it running. If there's an error during startup an error dialog will pop up.

To use it, simply copy the value you wish to convert, press Alt to activate the tool (**don't** hold down the Alt key). Then you need to select the *to* and *from* conversion types respectively. You can do so by pressing one of the keys shown in the table bellow. After you have selected both types the clipboard will contain the result. In case of an error, the clipboard will contain the error message.

## Supported numeric bases and binary encodings

| Key | Type |
|:---:|:----:|
| B | Binary |
| D | Decimal |
| H | Hexadecimal |
| O | Octal |
| C | BCD Code |
| I | XS-3 |
| G | Gray Code |

## Usage Example
Let's say we want to convert 15,3<sub>10</sub> (keep in mind we are using the comma (,) symbol as the decimal separator and **NOT** the dot (.) symbol) into a binary representation.

First we need to copy the value into the clipboard. This can be done by selecting the value and pressing Ctrl + C or rightclicking and selecting *Copy*.
Then we press *Alt* to activate the tool, followed by *D* (to indicate the input data is decimal) and then B (to indicate we want the output to be binary).
Lastly, we need to paste the result. This can be done by pressing Ctrl + V or right clicking and selecting *Paste*. In this case we see `1111,01010` as the result.

## Change Precision
Precision is by default set to 5 decimal places. To change it, first copy the new precision into the clipboard (e.g. `10`), activate the tool by pressing *Alt*, and press *P* to set the precision. A success / error message will be placed into the clipboard.