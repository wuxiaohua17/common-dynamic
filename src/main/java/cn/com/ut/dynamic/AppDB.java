package cn.com.ut.dynamic;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = { "key" })
public class AppDB {

	private String url;
	private String username;
	private String password;
	private String key;
}
