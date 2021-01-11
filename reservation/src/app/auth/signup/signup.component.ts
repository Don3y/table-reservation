import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';

import { AuthService } from '@core/services/auth.service';
import { NotificationService } from '@core/services/notification.service';

import { User } from '@core/interfaces/user.interface';

const RegExpValidator = {
	'lowerCase': RegExp(/^(?=.*?[a-z])/),
	'upperCase': RegExp(/^(?=.*?[A-Z])/),
	'digit': RegExp(/^(?=.*?[0-9])/),
	'specialChar': RegExp(/^(?=.*?[" !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~"])/)
};

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {
  public signupForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    protected auth: AuthService,
		private ns: NotificationService
  ) {
    this.signupForm = this.formBuilder.group({
			username: [null, [Validators.minLength(5), Validators.required]],
			password: [null, [Validators.pattern(RegExpValidator.lowerCase), Validators.pattern(RegExpValidator.upperCase), Validators.pattern(RegExpValidator.digit), Validators.pattern(RegExpValidator.specialChar), Validators.minLength(8), Validators.maxLength(30), Validators.required]],
		});
  }

  signup(form: FormGroup): void {
		if (form.valid) {
      this.auth.register(<User>form.value);
      this.signupForm.reset();
		}
		else {
			this.ns.show('Error! Data is not correct!');
		}
	}

}
