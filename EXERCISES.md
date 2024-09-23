## Reasoning about GCD

## Exercise 27
A program will crash if it ever divides by zero. Does this program ever crash due to division by zero?
Carefully explain how you figured this out. Explain briefly how an abstract interpretation could determine this fact.

### Solution

The program will never crash due to division by zero error.


#### Abstract domain
$\{\top,\space non-zero,\space zero, \space\bot\}$ where $\top$ denotes that the value may or may not be zero and non-zero means that the value must be something other than zero.

#### Transfer function

Mapping function $\alpha: \Z \to S$ where S is the abstract domain. This function maps a concrete integer value to an abstract value. E.g., $\alpha(2) = $ non-zero or $\alpha(0) =$ zero

Division operation:

| **/**        | **⊥** | **zero** | **non-zero** | **⊤**   |
|--------------|-------|----------|--------------|---------|
| **⊥**        | ⊥     | ⊥        | ⊥            | ⊥       |
| **zero**     | ⊥     | ⊥        | zero         | ⊥       |
| **non-zero** | ⊥     | ⊥        | ⊤            | ⊥       |
| **⊤**        | ⊥     | ⊥        | ⊤            | ⊥       |


Since the program uses divion operations where the denominator is always constant it, this abstract interpretation would be enough to establish the fact that division by zero never occurs.


## Exercise 29
Give an abstract interpretation that catches possible divide-by-zero errors statically. You do not need to
give transfer functions for every integer operation; addition, multiplication, and division will suffice. Think carefully about your choices; the design space is quite large!

You may assume that (1) we only care about integers (not floating-point values), and (2) overflow does not happen.
Briefly contrast your design to an alternative you chose. Name one way in which your design is better than the
alternative, and one way in which it is worse.

### Solution

There are four design alternatives I considered. They are as follows:

1. {non-zero, $\top$}
2. {$\bot$, zero, non-zero, $\top$}
3. {$\bot$, zero, positive, negative, non-zero, $\top$}
4. $\{[a,b]∣ a,b ∈ \Z ,a≤b\}$

Option 4 is the most precise because it allows for obtaining a lower bound on some operations than option 3 (e.g., positive + negative = $\top$, but $x + y < 0$, if $-100 \le x <-10$ and $1 \le y \le 5$ ).

However, option 3 provides a simpler model for implementation, so this is the one I will use.

#### Transfer functions

Addition:

|       | bottom | zero   | positive | negative | non-zero | top  |
|-------|--------|--------|----------|----------|----------|------|
| bottom | bottom | bottom | bottom   | bottom   | bottom   | bottom |
| zero  | bottom | zero   | positive | negative | non-zero | top  |
| positive | bottom | positive | positive | top      | top      | top  |
| negative | bottom | negative | top      | negative | top      | top  |
| non-zero | bottom | non-zero | top      | top      | top      | top  |
| top   | bottom | top    | top      | top      | top      | top  |


Subtraction:

|       | bottom | zero   | positive | negative | non-zero | top  |
|-------|--------|--------|----------|----------|----------|------|
| bottom | bottom | bottom | bottom   | bottom   | bottom   | bottom |
| zero  | bottom | zero   | negative | positive | non-zero | top  |
| positive | bottom | positive | positive | top      | top      | top  |
| negative | bottom | negative | top      | negative | top      | top  |
| non-zero | bottom | non-zero | top      | top      | top      | top  |
| top   | bottom | top    | top      | top      | top      | top  |


Division:

|       | bottom | zero   | positive | negative | non-zero | top  |
|-------|--------|--------|----------|----------|----------|------|
| bottom | bottom | bottom | bottom   | bottom   | bottom   | bottom |
| zero  | bottom | bottom | zero     | zero     | zero     | bottom |
| positive | bottom | bottom | positive | negative | non-zero | bottom |
| negative | bottom | bottom | negative | positive | non-zero | bottom |
| non-zero | bottom | bottom | non-zero | non-zero | non-zero | bottom |
| top   | bottom | bottom | top      | top      | top      | bottom |



Multiplication

|       | bottom | zero   | positive | negative | non-zero | top  |
|-------|--------|--------|----------|----------|----------|------|
| bottom | bottom | bottom | bottom   | bottom   | bottom   | bottom |
| zero  | bottom | zero   | zero     | zero     | zero     | zero |
| positive | bottom | zero   | positive | negative | non-zero | top  |
| negative | bottom | zero   | negative | positive | non-zero | top  |
| non-zero | bottom | zero   | non-zero | non-zero | non-zero | top  |
| top   | bottom | zero   | top      | top      | top      | top  |

