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

## Exercise 28
Stein’s algorithm is intended to compute a value; it should always terminate. Is it possible for this program
to run forever? Explain your reasoning. Explain briefly how an abstract interpretation could determine this fact.

### Solution
TODO

## Exercise 29
Give an abstract interpretation that catches possible divide-by-zero errors statically. You do not need to
give transfer functions for every integer operation; addition, multiplication, and division will suffice. Think carefully about your choices; the design space is quite large!

You may assume that (1) we only care about integers (not floating-point values), and (2) overflow does not happen.
Briefly contrast your design to an alternative you chose. Name one way in which your design is better than the
alternative, and one way in which it is worse.

### Solution

There are four design alternatives I considered. They are as follows:

1. {non-zero, $\bot$}
2. {$\top$, zero, non-zero, $\bot$}
3. {$\top$, zero, positive, negative, $\bot$}
4. $\{[a,b]∣ a,b ∈ \Z ,a≤b\}$

Option 4 is the most precise because it allows for obtaining a lower bound on some operations than option 3 (e.g., positive + negative = $\top$, but $x + y < 0$, if $-100 \le x <-10$ and $1 \le y \le 5$ ).

However, option 3 provides a simpler model for implementation, so this is the one I will use.

Lattice:



$\space \space \space \space \space \space \space \space \space \space \space \space \space \space \space \space \space \{\top\}$

$\space \space \space \space \space \space \space \space \space \space \space /\space \space \space \space \space \space \space |\space \space \space \space \space \space \space \space \backslash$

$\{zero\} \space \{negative\} \space \{positive\}$

$\space \space \space \space \space \space \space \space \space \space \space \backslash \space \space \space \space \space \space \space |\space \space \space \space \space \space \space \space /$

$\space \space \space \space \space \space \space \space \space \space \space \space \space \space \space \space \space \{\bot\}$


#### Transfer functions

Addition:

| **+**          | **⊥** | **negative** | **zero** | **positive** | **⊤**   |
|----------------|-------|--------------|----------|--------------|---------|
| **⊥**          | ⊥     | ⊥            | ⊥        | ⊥            | ⊥       |
| **negative**   | ⊥     | negative     | negative | ⊤            | ⊤       |
| **zero**       | ⊥     | negative     | zero     | positive     | ⊤       |
| **positive**   | ⊥     | ⊤            | positive | positive     | ⊤       |
| **⊤**          | ⊥     | ⊤            | ⊤        | ⊤            | ⊤       |

Division:

| **/**          | **⊥** | **negative** | **zero** | **positive** | **⊤**   |
|----------------|-------|--------------|----------|--------------|---------|
| **⊥**          | ⊥     | ⊥            | ⊥        | ⊥            | ⊥       |
| **negative**   | ⊥     | positive     | ⊥        | negative     | ⊥       |
| **zero**       | ⊥     | zero         | ⊥        | zero         | ⊥       |
| **positive**   | ⊥     | negative     | ⊥        | positive     | ⊥       |
| **⊤**          | ⊥     | ⊤            | ⊥        | ⊤            | ⊥       |

Multiplication

| **×**          | **⊥** | **negative** | **zero** | **positive** | **⊤**   |
|----------------|-------|--------------|----------|--------------|---------|
| **⊥**          | ⊥     | ⊥            | ⊥        | ⊥            | ⊥       |
| **negative**   | ⊥     | positive     | zero     | negative     | ⊤       |
| **zero**       | ⊥     | zero         | zero     | zero         | zero    |
| **positive**   | ⊥     | negative     | zero     | positive     | ⊤       |
| **⊤**          | ⊥     | ⊤            | zero     | ⊤            | ⊤       |

